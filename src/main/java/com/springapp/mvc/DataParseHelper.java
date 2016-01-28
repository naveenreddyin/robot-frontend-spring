package com.springapp.mvc;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.springapp.mvc.model.KeyValueObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Raquibul on 1/28/2016.
 */
@Service
public class DataParseHelper
{

    List<String> keyWordList =
            Lists.newArrayList(
        "EBITDA","EBIT","Operating cash flow","Order intake", "Operating revenue");

    List<String> timeDurationList = Lists.newArrayList("year","quarter");

    private static final Log logger = LogFactory.getLog(DataParseHelper.class);
    List<KeyValueObject> parseFinancialData(String dataToBeParse){
        List<KeyValueObject> sentenceList = new ArrayList<KeyValueObject>();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);

        iterator.setText(dataToBeParse);
        int start = iterator.first();
        String currentTimeline = null;
        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {
            String sentence = dataToBeParse.substring(start, end);
            String timeline = findTimeLine(sentence);
            if(!Strings.isNullOrEmpty(timeline)){
                if(timeline.equalsIgnoreCase("quarter")){
                    //find which quarter it is
                    String substring = sentence.substring(0, sentence.indexOf("quarter"));
                    List<String> splitted = Splitter.on(" ").omitEmptyStrings().splitToList(substring);
                    String quarterName = splitted.get(splitted.size()-1);
                    logger.info("QuarterName "+quarterName);
                    currentTimeline = quarterName+" quarter";

                }else if(timeline.equalsIgnoreCase("year")){
                    //lets find out the year
                    currentTimeline = "year";
                }
            }
//            sentenceList.add(sentence);
            //lets process the sentence
            String keyWordFound = null;
            while((keyWordFound = findFirstKeyWord(sentence))!=null) {
//                    logger.info("Found Keyword: "+sentence);
                    String data1 = sentence.substring(0, sentence.indexOf(")") + 1);
                    String data2 = Lists.newArrayList(Splitter.on("NOK").split(data1)).get(1);
                    logger.info(keyWordFound + ": " + data2);
                KeyValueObject keyValueObject= new KeyValueObject(keyWordFound, data2);
                keyValueObject.setTimeline(currentTimeline);
                sentenceList.add(keyValueObject);
               sentence = sentence.substring(sentence.indexOf(")")+1);
//                logger.info("sentence: "+sentence);
            }
//            logger.info("["+sentence+"]");
        }
        return sentenceList;
    }

    String findFirstKeyWord(String sentence){
        for(String keyword:keyWordList){
            if(sentence.contains(keyword))
                return keyword;
        }
        return null;
    }

    String findTimeLine(String sentence){
        for(String keyword:timeDurationList){
            if(sentence.contains(keyword))
                return keyword;
        }
        return null;
    }
}
