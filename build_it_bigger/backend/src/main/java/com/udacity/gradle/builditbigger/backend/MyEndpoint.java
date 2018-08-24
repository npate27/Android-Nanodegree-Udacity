package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.neelhpatel.javajokelib.JokesContent;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** A simple endpoint method that returns a MyBean Object with a joke string set*/
    @ApiMethod(name = "getJoke")
    public MyBean getJoke() {
        MyBean myBean = new MyBean();
        myBean.setData(new JokesContent().getRandomJoke());
        return myBean;
    }

}
