package com.ant.test;

import com.ant.minis.web.bind.WebDataBinder;
import com.ant.minis.web.bind.support.WebBindingInitializer;
import com.ant.test.CustomDateEditor;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/4 9:26
 */
public class DateInitializer implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class, "yyyy-MM-dd", false));
    }
}
