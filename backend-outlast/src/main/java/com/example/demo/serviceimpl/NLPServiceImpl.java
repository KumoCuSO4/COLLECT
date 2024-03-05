package com.example.demo.serviceimpl;

import com.example.demo.service.NLPService;
import com.example.demo.util.Word2VecUtil;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.Vector;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NLPServiceImpl implements NLPService {
    @Override
    public float calculateSimilarity(String str1, String str2) {
        DocVectorModel docVectorModel = Word2VecUtil.getDocVectorModel();
        return docVectorModel.similarity(str1, str2);
    }

    @Override
    public Vector getVector(String str) {
        return null;
    }

    @Override
    public double calculateAbility(String str) {
        double ability = 0.0;
        List<Term> termList = NLPTokenizer.segment(str);
        CoreStopWordDictionary.apply(termList);
        for(Term term : termList) {
            if(Word2VecUtil.getAbilityList().contains(term.word)) {
                ability+=0.01;
            }
        }
        return ability;
    }
}
