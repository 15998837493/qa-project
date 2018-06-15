
package com.qa.master.dao;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.qa.BaseJunit;

public class DiseaseOptionDAOTest extends BaseJunit {

    @Test
    public void testCalculateDiseaseScore() {
        List<String> optionIdList = new ArrayList<String>();
        optionIdList.add("Q001P022A002");
        optionIdList.add("Q001P022A003");
        optionIdList.add("Q001P022A003");
        optionIdList.add("Q001P022A004");
        Integer score = null;
        try {
            score = diseaseOptionDAO.calculateDiseaseScore("D0001", optionIdList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> optionIdList2 = new ArrayList<String>();
        optionIdList2.retainAll(optionIdList);
        assertNull("测试结果为：" + score, score);
    }
}
