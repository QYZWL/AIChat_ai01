package com.yutest.yu_ai01.ai.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ragConfig {
    @Resource
    private EmbeddingModel qwenEmbeddingModel;

    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    @Bean
    public ContentRetriever contentRetriever(){
        // ------ RAG ------
        // 1. 加载文档
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("src/main/resources/docs");

        // 2. 文档切割
        DocumentByParagraphSplitter documentByParagraphSplitter =
                new DocumentByParagraphSplitter(1000,10);
        // 3. 自定义文档加载器，文档存储，存入向量数据库
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                // 指定文档切割器
                .documentSplitter(documentByParagraphSplitter)
                // 优化文档质量
                .textSegmentTransformer(textSegment -> TextSegment.from(textSegment.metadata().getString("file_name") +
                        "\n" + textSegment.text(), textSegment.metadata()))
                // 指定向量模型
                .embeddingModel(qwenEmbeddingModel)
                //指定向量存储
                .embeddingStore(embeddingStore)
                .build();

        // 4. 加载文档
        ingestor.ingest(documents);

        // 5. 自定义内容加载器
        EmbeddingStoreContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(qwenEmbeddingModel)
                .maxResults(10)
                .minScore(0.75)
                .build();

        return contentRetriever;
    }
}
