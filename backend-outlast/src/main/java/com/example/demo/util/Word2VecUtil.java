package com.example.demo.util;

import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.Word2VecTrainer;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Word2VecUtil {
    private static WordVectorModel wordVectorModel;
    private static DocVectorModel docVectorModel;

    private static List<String> abilityList;

    static {
        try {
            wordVectorModel = new WordVectorModel("data/model.txt");
            System.out.println("模型加载成功");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("模型加载失败，开始使用数据集训练");
            Word2VecTrainer trainerBuilder = new Word2VecTrainer();
            wordVectorModel = trainerBuilder.train("data/training.txt", "data/model.txt");
        }
        docVectorModel = new DocVectorModel(wordVectorModel);


        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data/ability.txt"),"UTF-8"));
            abilityList = Arrays.asList(br.lines().toArray(String[]::new));
            //System.out.println(abilityList);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static WordVectorModel getWordVectorModel() {
        return wordVectorModel;
    }

    public static DocVectorModel getDocVectorModel() {
        return docVectorModel;
    }

    public static List<String> getAbilityList() {return abilityList;}
}
