package com.hung.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author itsol.hungtt
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    public static final String FN_GET_STORE_INFO_BY_BRAND  = "fn_get_store_info_by_brand";
    public static final String FN_GET_RECEIVE_INFO_PREDAY   = "fn_get_receive_info_preday";
    public static final String FN_GET_OUTPUT_ENTRY_BY_BATCH   = "fn_get_output_entry_by_batch";

    public static final String FN_GET_DOC_ENTRY_INFO   = "fn_get_doc_entry_info";
    public static final String FN_GET_VAB_STORE_ENTRY  = "fn_get_vab_store_entry";
    public static final String PRC_GET_ACQT_USED_FOLLOW_REPORT  = "prc_get_acqt_used_follow_report";

    public static final String FN_GET_BLANK_ENTRY_DOC  = "fn_get_blank_entry_doc";


    public static String getGraphQLCommand(String fileName) {
        try {
            Resource resource = new ClassPathResource("graphql/" + fileName + ".graphql");
            InputStream input = resource.getInputStream();
            return readFromInputStream(input);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public static String getFile(String filePath) {
        try {
            Resource resource = new ClassPathResource(filePath);
            InputStream input = resource.getInputStream();
            return readFromInputStream(input);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
