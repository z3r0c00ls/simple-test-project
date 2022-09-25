package com.revelo.simpletestproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServletInitializerTest {

    @Mock
    private SpringApplicationBuilder springApplicationBuilder;

    @Test
    public void testServletInitializerTest() {
        ServletInitializer servletInitializer = new ServletInitializer();
        when(springApplicationBuilder.sources(SimpleTestProjectApplication.class)).thenReturn(springApplicationBuilder);

        SpringApplicationBuilder result = servletInitializer.configure(springApplicationBuilder);

        verify(springApplicationBuilder).sources(SimpleTestProjectApplication.class);
        assertEquals(springApplicationBuilder,result);
    }

}
