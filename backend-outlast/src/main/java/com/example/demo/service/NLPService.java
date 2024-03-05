package com.example.demo.service;

import com.hankcs.hanlp.mining.word2vec.Vector;

public interface NLPService {
    /**
     * 计算两个String的相似度
     * @param str1
     * @param str2
     * @return similarity
     */
    float calculateSimilarity(String str1, String str2);

    /**
     * 获取特征向量（暂时没用）
     * @param str
     * @return Vector
     */
    Vector getVector(String str);

    double calculateAbility(String str);
}
