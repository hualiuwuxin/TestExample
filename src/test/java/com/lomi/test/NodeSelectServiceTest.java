package com.lomi.test;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 选择器覆盖率87%
 */
public class NodeSelectServiceTest {


    @Test
    public void select(){
        NodeSelectService nodeSelectService = new NodeSelectService();
        nodeSelectService.select(1);
        nodeSelectService.select(-1); 

    }

}