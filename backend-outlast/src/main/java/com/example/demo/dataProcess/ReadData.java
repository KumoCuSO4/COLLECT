package com.example.demo.dataProcess;

import com.csvreader.CsvReader;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.collection.dartsclone.Pair;
import com.hankcs.hanlp.corpus.occurrence.TermFrequency;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
//import javafx.util.Pair;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadData {
    static Map<String, Integer> termMap = new HashMap<>();
    public static void loadFile (String fileName){
        //Map<String, Integer> termMap = new HashMap<>();
        System.out.println( "Data file name : " + fileName );

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"gbk"));

            CsvReader reader = new CsvReader( br, ',');

            reader.readHeaders();
            while ( reader.readRecord() ){
                // String testCaseName = reader.get("case标题");
                // String phoneType = reader.get("机型");
                // String OS = reader.get("操作系统");
                String bugDetail = reader.get("bug详情");
                String reproSteps = reader.get("复现步骤");

                List<Term> termList = NLPTokenizer.segment(reproSteps);
                termList.addAll(NLPTokenizer.segment(bugDetail));
                CoreStopWordDictionary.apply(termList);
                //System.out.println(termList);
                for(Term term : termList) {
                    if(!termMap.containsKey(term.word)) {
                        termMap.put(term.word,1);
                    }
                    else {
                        termMap.replace(term.word,termMap.get(term.word)+1);
                    }
                }
                //List<String> keywordList = HanLP.extractKeyword(bugDetail, 5);
                //System.out.println(keywordList);
            }
            reader.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        for(String word : termMap.keySet()) {
            System.out.println(word + " " + termMap.get(word));
        }
         */
    }

    public static void loadFileDir(String dir) {
        File directory = new File(dir);
        if(!directory.isDirectory()) return;
        File[] files = directory.listFiles();
        assert files != null;
        for(File file : files) {
            try{
                loadFile(file.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static Comparator<Pair<String,Integer>> cmp = (o1, o2) -> o2.getValue() - o1.getValue();

    public static void main(String[] args) {
        loadFileDir("data/train");
        PriorityQueue<Pair<String,Integer>> queue = new PriorityQueue<>(cmp);
        for(String word : termMap.keySet()) {
            if(termMap.get(word)<=20) continue;
            queue.add(new Pair<>(word,termMap.get(word)));
            //System.out.println(word + " " + termMap.get(word));
        }
        System.out.println(queue);
    }
}
