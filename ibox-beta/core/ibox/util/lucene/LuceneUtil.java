package ibox.util.lucene;

import ibox.util.properties.PropertiesContent;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.ibox.bean.LuceneBean;


/**
 * IKAnalyzer分词和Lucene检索
 * 
 * @author cherry
 * 
 */
public class LuceneUtil {

	private static final Logger logger = Logger.getLogger(LuceneUtil.class);

	private static String LucenePath = null;
	private static Integer ResHits = 0;
	private static IndexSearcher searcher = null;
	private static File indexFile = null;
	private static Analyzer analyzer = null;

	static {
		LucenePath =PropertiesContent.get("lucenePath");
		ResHits = 800;// 最多显示100页，700条搜索结果
	}

	/**
	 * 获取indexSearcher
	 * 
	 * @return
	 * @throws Exception
	 */
	public static IndexSearcher getIndexSearcher() throws Exception {
		if (null == searcher) {
			indexFile = new File(LucenePath);
			Directory directory = FSDirectory.open(indexFile);
			IndexReader ireader = DirectoryReader.open(directory);
			searcher = new IndexSearcher(ireader);
		}
		searcher.setSimilarity(new IKSimilarity());
		return searcher;
	}

	/**
	 * 创建索引(只创建一次)
	 * 
	 * @Title: createIndex
	 * @author Realfighter
	 * @param list
	 * @return
	 * @throws Exception int
	 */
	public static int createIndex(List<?> list) throws Exception {
		Directory directory = null;
		IndexWriter indexWriter = null;

		indexFile = new File(LucenePath);
		if (!indexFile.exists()) {
			indexFile.mkdir();
		} else {
			deleteDir(indexFile);
		}
		logger.info("索引存放位置：" + indexFile.getAbsolutePath());
		directory = FSDirectory.open(indexFile);
		analyzer = new IKAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_45,
				analyzer);
		indexWriter = new IndexWriter(directory, config);
		FieldType fieldType = new FieldType();
		fieldType.setIndexed(true);// 是否索引
		fieldType.setStored(true);// 是否存储
		fieldType.setTokenized(false);// 是否分类
		fieldType.setOmitNorms(false);
		Document doc = null;
		for (int i = 0; i < list.size(); i++) {
			LuceneBean luceneBean = (LuceneBean) list.get(i);
			doc = new Document();
			Field id = new Field("id", String.valueOf(luceneBean.getId()),
					fieldType);
			Field name = new Field("name", luceneBean.getName(),
					fieldType);
			Field email = new Field("email", luceneBean.getEmail(),
					fieldType);
			doc.add(id);
			doc.add(name);
			doc.add(email);
			indexWriter.addDocument(doc);
		}
		indexWriter.commit();
		/** 查看IndexWriter里面有多少个索引 **/
		int num = indexWriter.maxDoc();
		indexWriter.close();
		return num;
	}

	/**
	 * 添加索引
	 * 
	 * @Title: addIndex
	 * @author Realfighter
	 * @param luceneBean
	 * @return
	 * @throws Exception int
	 */
	public static void addIndex(LuceneBean luceneBean) throws Exception {
		Directory directory = null;
		IndexWriter indexWriter = null;
		indexFile = new File(LucenePath);
		directory = FSDirectory.open(indexFile);
		analyzer = new IKAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_45,
				analyzer);
		indexWriter = new IndexWriter(directory, config);
		FieldType fieldType = new FieldType();
		fieldType.setIndexed(true);// set是否索引
		fieldType.setStored(true);// set是否存储
		Document doc = null;
		doc = new Document();
		Field id = new Field("id",luceneBean.getId(),
				fieldType);
		Field name = new Field("name", luceneBean.getName(),
				fieldType);
		Field email = new Field("email", luceneBean.getEmail(),
				fieldType);
		doc.add(id);
		doc.add(name);
		doc.add(email);
		indexWriter.addDocument(doc);
		indexWriter.commit();
		indexWriter.close();
	}

	private static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				deleteDir(new File(dir, children[i]));
			}
		}
		dir.delete();
	}

	/**
	 * 更新索引
	 * 
	 * @Title: updateIndex
	 * @author Realfighter
	 * @param luceneBean
	 * @throws Exception void
	 */
	public static void updateIndex(LuceneBean luceneBean) throws Exception {
		/** 这里放索引文件的位置 **/
		indexFile = new File(LucenePath);
		analyzer = new IKAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_45,
				analyzer);
		Directory directory = FSDirectory.open(indexFile);
		IndexWriter indexWriter = new IndexWriter(directory, config);

		/** 增加document到索引去 **/
		FieldType fieldType = new FieldType();
		fieldType.setIndexed(true);// set 是否索引
		fieldType.setStored(true);// set 是否存储
		Document doc = new Document();
		Field id = new Field("id", String.valueOf(luceneBean.getId()),
				fieldType);
		Field name = new Field("name", luceneBean.getName(),
				fieldType);
		Field email = new Field("email", luceneBean.getEmail(),
				fieldType);
		doc.add(id);
		doc.add(name);
		doc.add(email);
		Term term = new Term("id", luceneBean.getId());
		indexWriter.updateDocument(term, doc);
		/** optimize()方法是对索引进行优化 **/
		indexWriter.close();
	}

	/**
	 * 删除索引
	 * 
	 * @Title: deleteIndex
	 * @author Realfighter
	 * @param luceneBean
	 * @throws Exception void
	 */
	public static void deleteIndex(LuceneBean luceneBean) throws Exception {
		/** 这里放索引文件的位置 **/
		indexFile = new File(LucenePath);
		analyzer = new IKAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_45,
				analyzer);
		Directory directory = FSDirectory.open(indexFile);
		IndexWriter indexWriter = new IndexWriter(directory, config);

		Term term = new Term("id", luceneBean.getId());
		indexWriter.deleteDocuments(term);
		/** optimize()方法是对索引进行优化 **/
		indexWriter.close();
	}

	/**
	 * 检索文章beans
	 * 
	 * @Title: search
	 * @author cherry
	 * @param word
	 * @return List<LuceneBean>
	 */
	public static String search(String keyword,String email) {
		//List<LuceneBean> list = new ArrayList<LuceneBean>();
		TopDocs topDocs = null;
		StringBuffer ids = new StringBuffer();
		String search ="";
		
		try {
			indexFile = new File(LucenePath);
			Directory directory = FSDirectory.open(indexFile);
			keyword = QueryParser.escape(keyword);
			IndexSearcher searcher =new IndexSearcher(DirectoryReader.open(directory));
			String[] queries = {keyword, email};
			String[] fields = { "name", "email" };
			BooleanClause.Occur[] flags = { BooleanClause.Occur.MUST,
					BooleanClause.Occur.SHOULD };
			Query query = MultiFieldQueryParser.parse(Version.LUCENE_45,
					queries, fields, flags, new IKAnalyzer());
			if (searcher != null) {
				/** hits结果 **/
				topDocs = searcher.search(query, ResHits);
				ScoreDoc[] scoreDocs = topDocs.scoreDocs;
				logger.info("当前关键字【" + keyword + "】lucene检索结果：" + scoreDocs.length
						+ "条。");
				for (int i = 0; i < scoreDocs.length; i++) {
					int docId = scoreDocs[i].doc;
					Document doc = searcher.doc(docId);
					if(!doc.get("email").equals(email)){
						continue;
					}
					ids.append("'");
					ids.append(doc.get("id"));
					ids.append("'");
					ids.append(",");
				}
				if (ids.length() > 0) {
					search = ids.substring(0, ids.length() - 1);
				}
			}
		} catch (Exception e) {
			logger.error("当前关键字【" + keyword + "】通过lucene检索异常：" + e);
		}
		return search;
	}

	/**
	 * 检索文章IDs
	 * 
	 * @Title: searchString
	 * @author Realfighter
	 * @param word
	 * @return String
	 */
	public static String searchString(String word) {
		String search = "";
		StringBuffer ids = new StringBuffer();
		TopDocs topDocs = null;
		try {
			word = QueryParser.escape(word);
			indexFile = new File(LucenePath);
			Directory directory = FSDirectory.open(indexFile);
			IndexSearcher searcher =new IndexSearcher(DirectoryReader.open(directory));
			String[] queries = { word};
			String[] fields = { "name"};
			BooleanClause.Occur[] flags = { BooleanClause.Occur.SHOULD};
			
			Query query = MultiFieldQueryParser.parse(Version.LUCENE_45,
					queries, fields, flags, new IKAnalyzer());
			if (searcher != null) {
				/** hits结果 **/
				topDocs = searcher.search(query, ResHits);
				ScoreDoc[] scoreDocs = topDocs.scoreDocs;
				logger.info("当前关键字【" + word + "】lucene检索结果：" + scoreDocs.length
						+ "条。");
				Document doc = null;
				for (int i = 0; i < scoreDocs.length; i++) {
					int docId = scoreDocs[i].doc;
					doc = searcher.doc(docId);
					ids.append("'");
					ids.append(doc.get("id"));
					ids.append("'");
					ids.append(",");
				}
				if (ids.length() > 0) {
					search = ids.substring(0, ids.length() - 1);
				}
			}
		} catch (Exception e) {
			logger.error("当前关键字【" + word + "】通过lucene检索异常：" + e);
		}
		return search;
	}
}
