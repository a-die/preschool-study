import pandas as pd
import numpy as np
from django.http import HttpResponse, JsonResponse
import pymysql
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import jieba



def UserRecommendationAlgorithm(request):
    if request.method == 'POST':
        userId = request.POST.get('userId',1)
    else:
        userId = request.GET.get('userId',1)
    userId = int(userId)
    print(userId)
    dtype = {"userId": np.int32, "discussPostId": np.int32, "rating": np.float32}
    ratings = pd.read_csv("static/ratings.csv", dtype=dtype ,usecols=range(3))
    print(ratings)
    # 构建透视表 找到用户和收藏文章之间的关系
    ratings_matrix = ratings.pivot_table(index=['userId'], columns=['discussPostId'], values='rating')
    print(ratings_matrix)
    user_similar = ratings_matrix.T.corr()

    print(user_similar)
    # 为每一个用户找到最相似的5个用户
    topN_users = {}
    for i in user_similar.index:
        # 取出每一列数据， 删除自己，按照相似度排序
        _df = user_similar.loc[i].drop([i])
        _df_sorted = _df.sort_values(ascending=False)
        top2 = list(_df_sorted.index[:5])
        topN_users[i] = top2
    print(topN_users[userId])

    return JsonResponse(topN_users[userId],safe=False)


def predict(uid, iid, ratings_matrix, user_similar):
    '''
    预测给定用户对给定物品的评分值
    :param uid: 用户ID
    :param iid: 物品ID
    :param ratings_matrix: 用户-物品评分矩阵
    :param user_similar: 用户两两相似度矩阵
    :return: 预测的评分值
    '''
    print("开始预测用户<%d>对电影<%d>的评分..." % (uid, iid))
    # 1. 找出uid用户的相似用户
    similar_users = user_similar[uid].drop([uid]).dropna()
    # 相似用户筛选规则：正相关的用户
    similar_users = similar_users.where(similar_users > 0).dropna()
    if similar_users.empty is True:
        raise Exception("用户<%d>没有相似的用户" % uid)

    # 2. 从uid用户的近邻相似用户中筛选出对iid物品有评分记录的近邻用户
    ids = set(ratings_matrix[iid].dropna().index) & set(similar_users.index)
    finally_similar_users = similar_users.loc[list(ids)]

    # 3. 结合uid用户与其近邻用户的相似度预测uid用户对iid物品的评分
    sum_up = 0  # 评分预测公式的分子部分的值
    sum_down = 0  # 评分预测公式的分母部分的值
    for sim_uid, similarity in finally_similar_users.iteritems():
        # 近邻用户的评分数据
        sim_user_rated_movies = ratings_matrix.loc[sim_uid].dropna()
        # 近邻用户对iid物品的评分
        sim_user_rating_for_item = sim_user_rated_movies[iid]
        # 计算分子的值
        sum_up += similarity * sim_user_rating_for_item
        # 计算分母的值
        sum_down += similarity

    # 计算预测的评分值并返回
    predict_rating = sum_up / sum_down
    print("预测出用户<%d>对电影<%d>的评分：%0.2f" % (uid, iid, predict_rating))
    return round(predict_rating, 2)

def cut_word(text):
    """
    进行中文分词 "我爱北京天安门"——"我 爱 北京 天安门"
    :param text:
    :return:
    """
    # <generator object Tokenizer.cut at 0x0000020C8E57F7B0>
    # print(jieba.cut(text))
    # print(jieba.lcut(text), type(jieba.lcut(text)))
    a = " ".join(jieba.cut(text))
    return " ".join(jieba.cut(text))


def test():
    content_new = cut_word("测试.分割自杀")
    sensitive = {
        '傻逼', '吸毒', '自杀'
    }
    for x in content_new.split(' '):
        for y in sensitive:
            if x.count(y) > 0:
                print(0)
    print(1)



def checkLegal(request):
    contentGet = request.POST.get('content', "默认没有")
    content_new = cut_word(contentGet)
    print(content_new)
    sensitive = {
        '傻逼', '吸毒', '自杀'
    }
    for y in sensitive:
        if contentGet.count(y)>0:
            return HttpResponse(0)
    return HttpResponse(1)


def tfidf(request):
    # 创建连接
    conn = pymysql.connect(host='1.117.51.169', port=3306, user='root', passwd='123456',
                           db='preschool', charset='utf8')
    # 创建游标
    cursor = conn.cursor(cursor=pymysql.cursors.DictCursor)

    # 执行SQL，并返回收影响行数
    cursor.execute(
        "select * from discuss_post")
    discussPostList = cursor.fetchall()
    # 关闭游标
    cursor.close()
    # 关闭连接
    conn.close()

    # print(type(discussPostList[0]),discussPostList[0])
    # print(discussPostList[0]['content'])

    contentGet = request.POST.get('content',"默认没有")

    content = []
    content.append(cut_word(contentGet))


    contentList = []

    for item in discussPostList:
        # print(item['content'])
        contentList.append(cut_word(item['content'])) # 将该文章内容加入



    # print(content)
    # print(contentList)

    # 停顿词可以去掉，对比的时候不会考虑
    vectorizer = TfidfVectorizer(stop_words=["$",",",".","，","。","#","@","\n"," ","?","？","*","(",")","\\"])
    vectorizer.fit(content + contentList)
    tfidf_0 = vectorizer.transform(content)
    tfidf_1 = vectorizer.transform(contentList)

    similarity_matrix = cosine_similarity(tfidf_0, tfidf_1)


    return HttpResponse(similarity_matrix)
