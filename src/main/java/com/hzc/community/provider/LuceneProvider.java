package com.hzc.community.provider;

import com.hzc.community.mapper.UserMapper;
import com.hzc.community.model.Question;
import com.hzc.community.model.Tag;
import com.hzc.community.model.UserModel;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queries.function.valuesource.IntFieldSource;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class LuceneProvider {

    @Autowired
    private UserMapper userMapper;
    public void addScore(Question question){
        String path=null;
        try {
            String root= ResourceUtils.getURL("classpath:").getPath()+"static";
            path=root+"/lucene/";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        Path relativePath=Paths.get(URI.create("file://"+path));
        Directory directory=null;
        try {
            directory=FSDirectory.open(relativePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document document=new Document();
        StoredField id=new StoredField("id",question.getId());
        TextField title=new TextField("title",question.getTitle(), Field.Store.YES);
        TextField description=new TextField("description",question.getDescription(), Field.Store.YES);
        StoredField creator=new StoredField("creator",question.getCreator());
        StoredField commentCount=new StoredField("commentCount",question.getCommentCount());
        StoredField viewCount=new StoredField("viewCount",question.getViewCount());
        StoredField likeCount=new StoredField("likeCount",question.getLikeCount());
        StoredField gmtCreate=new StoredField("gmtCreate",question.getGmtCreate());
        StoredField gmtModify=new StoredField("gmtModify",question.getGmtModify());
        TextField tag=new TextField("tags",question.getTag(), Field.Store.YES);
        document.add(id);
        document.add(title);
        document.add(description);
        document.add(creator);
        document.add(commentCount);
        document.add(viewCount);
        document.add(likeCount);
        document.add(gmtCreate);
        document.add(gmtModify);
        document.add(tag);
        Analyzer analyzer=new SmartChineseAnalyzer();
        IndexWriterConfig writerConfig=new IndexWriterConfig(analyzer);
        try {
            IndexWriter indexWriter=new IndexWriter(directory,writerConfig);
            indexWriter.addDocument(document);
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public List<Question> getScores(String key){
        String path=null;
        try {
            String root= ResourceUtils.getURL("classpath:").getPath()+"static";
            path=root+"/lucene/";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        Path relativePath=Paths.get(URI.create("file://"+path));
        Directory directory=null;
        try {
            directory=FSDirectory.open(relativePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexReader indexReader= null;
        try {
            indexReader=DirectoryReader.open(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexSearcher indexSearcher=new IndexSearcher(indexReader);
        MultiFieldQueryParser queryParser=new MultiFieldQueryParser(new String[]{"title","description","tag"},new SmartChineseAnalyzer());
        Query query=null;
        try {
            query=queryParser.parse(key);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Formatter formatter=new SimpleHTMLFormatter("<em>","</em>");
        Scorer scorer=new QueryScorer(query);
        Highlighter highlighter=new Highlighter(formatter,scorer);
        TopDocs topDocs=null;
        Analyzer analyzer=new SmartChineseAnalyzer();
        try {
            topDocs=indexSearcher.search(query,10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Question>questions=new ArrayList<>();
        ScoreDoc[]scoreDocs=topDocs.scoreDocs;
        for(ScoreDoc scoreDoc:scoreDocs){
            int docId=scoreDoc.doc;
            Document document=null;
            try {
                document=indexReader.document(docId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Question question=new Question();
            question.setId(Integer.valueOf(document.get("id")));
            String hTitle= null;
            try {
                hTitle = highlighter.getBestFragment(analyzer,"title",document.get("title"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidTokenOffsetsException e) {
                e.printStackTrace();
            }
            if(hTitle!=null) {
                question.setTitle(hTitle);
            }else {
                question.setTitle(document.get("title"));
            }
            String description=document.get("description");
            String hDescription=null;
            try {
                hDescription =highlighter.getBestFragment(analyzer,"description",description);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidTokenOffsetsException e) {
                e.printStackTrace();
            }
            if(hDescription!=null){
                question.setDescription(hDescription);
            }else{
                question.setDescription(description);
            }
            String tag=document.get("tags");
            String hTag=null;
            try {

                 hTag= highlighter.getBestFragment(analyzer,"tags",tag);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidTokenOffsetsException e) {
                e.printStackTrace();
            }
            if(hTag!=null){
                question.setTag(hTag);
                question.setTagArrays(hTag.split(","));
            }else {
                question.setTag(tag);
                question.setTagArrays(tag.split(","));
            }
            Long creator=Long.parseLong(document.get("creator"));
            UserModel user=userMapper.selectByAccount(creator);
            question.setUserModel(user);
            question.setViewCount(Integer.parseInt(document.get("viewCount")));
            question.setCommentCount(Integer.parseInt(document.get("commentCount")));
            question.setLikeCount(Integer.parseInt(document.get("likeCount")));
            question.setGmtCreate(Long.parseLong(document.get("gmtCreate")));
            question.setGmtModify(Long.parseLong(document.get("gmtModify")));
            questions.add(question);
        }
        return questions;
    }
}
