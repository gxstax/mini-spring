package com.ant.minis.webmvc.servlet;

import com.ant.minis.beans.BeansException;
import com.ant.minis.beans.factory.annotation.Autowired;
import com.ant.minis.web.XmlScanComponentHelper;
import com.ant.minis.web.context.WebApplicationContext;
import com.ant.minis.web.context.support.AnnotationConfigWebApplicationContext;
import com.ant.minis.web.method.HandlerMethod;
import com.ant.minis.webmvc.mvc.RequestMappingHandlerAdapter;
import com.ant.minis.webmvc.mvc.RequestMappingHandlerMapping;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Simple extension of {@link javax.servlet.http.HttpServlet} which treats
 * its config parameters ({@code init-param} entries within the
 * {@code servlet} tag in {@code web.xml}) as bean properties.
 * </p>
 *
 * @author Ant
 * @since 2023/11/27 18:33
 */
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
    public static final String HANDLER_MAPPING_BEAN_NAME = "handlerMapping";
    public static final String HANDLER_ADAPTER_BEAN_NAME = "handlerAdapter";

    private String sContextConfigLocation;

    /** 用于存储需要扫描的 package 列表 */
    private List<String> packageNames = new ArrayList<>();

    /** 用于存储controller 的名称和对象的映射关系 */
    private Map<String,Object> controllerObjs = new HashMap<>();

    /** 用于存储controller名称数组列表 */
    private List<String> controllerNames = new ArrayList<>();

    /** 用于存储 controller 名称与类的映射关系 */
    private Map<String, Class<?>> controllerClasses = new HashMap<>();

    private WebApplicationContext webApplicationContext;

    private WebApplicationContext parentApplicationContext;

    private HandlerMapping handlerMapping;

    private HandlerAdapter handlerAdapter;

    private ViewResolver viewResolver;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.parentApplicationContext = (WebApplicationContext)
                this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");

        URL xmlPath = null;

        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

        this.webApplicationContext = new AnnotationConfigWebApplicationContext(sContextConfigLocation, this.parentApplicationContext);

        refresh();
    }

    protected void refresh() {
        // 初始化 controller
//        initController();

        initHandlerMappings(this.webApplicationContext);

        initHandlerAdapters(this.webApplicationContext);
    }

    protected void initHandlerMappings(WebApplicationContext wac) {
        try {
            this.handlerMapping = (HandlerMapping) wac.getBean(HANDLER_MAPPING_BEAN_NAME);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    protected void initHandlerAdapters(WebApplicationContext wac) {
        try {
            this.handlerAdapter = (HandlerAdapter) wac.getBean(HANDLER_ADAPTER_BEAN_NAME);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    protected void initController() {
        // 扫描包，获取所有类名
        this.controllerNames = scanPackages(this.packageNames);
        for (String controllerName : controllerNames) {
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(controllerName);
                obj = clz.newInstance();
            } catch (Exception e) {}
            this.controllerClasses.put(controllerName, clz);

            try {
                // 实例化bean
                populateBean(obj);
                this.controllerObjs.put(controllerName, obj);
            } catch (Exception e) {}
        }
    }

    private void populateBean(Object bean) throws BeansException {
        Class<?> clz = bean.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            boolean isAutowired = field.isAnnotationPresent(Autowired.class);
            if (isAutowired) {
                String fieldName = field.getName();
                Object autowiredBean = this.webApplicationContext.getBean(fieldName);
                field.setAccessible(true);
                try {
                    field.set(bean, autowiredBean);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private List<String> scanPackages(List<String> packages) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packages) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URI uri = null;
        //将以.分隔的包名换成以/分隔的uri
        try {
            uri = this.getClass().getResource("/" +
                    packageName.replaceAll("\\.", "/")).toURI();
        } catch (Exception e) {
        }
        File dir = new File(uri);
        //处理对应的文件目录
        for (File file : dir.listFiles()) { //目录下的文件或者子目录
            if(file.isDirectory()){ //对子目录递归扫描
                scanPackage(packageName+"."+file.getName());
            }else{ //类文件
                String controllerName = packageName +"."
                        +file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);

        try {
            doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * <p>
     * 请求调度
     * </p>
     *
     * @param request
     * @param response
     * @return void
     */
    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest processedRequest = request;
        HandlerMethod handlerMethod = null;
        ModelAndView mv = null;

        handlerMethod = this.handlerMapping.getHandler(processedRequest);
        if (handlerMethod == null) {
            return;
        }

        HandlerAdapter ha = this.handlerAdapter;

        mv = ha.handle(processedRequest, response, handlerMethod);

        render(processedRequest, response, mv);
    }

    protected void render( HttpServletRequest request, HttpServletResponse response,ModelAndView mv) throws Exception {
        if (mv == null) {
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }

        String sTarget = mv.getViewName();
        Map<String, Object> modelMap = mv.getModel();
        View view = resolveViewName(sTarget, modelMap, request);
        view.render(modelMap, request, response);

    }

    protected View resolveViewName(String viewName, Map<String, Object> model,
                                   HttpServletRequest request) throws Exception {
        if (this.viewResolver != null) {
            View view = viewResolver.resolveViewName(viewName);
            if (view != null) {
                return view;
            }
        }
        return null;
    }
}
