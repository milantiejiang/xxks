package com.mltj.xxks.net;

import com.mltj.xxks.bean.Regist;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author milantiejiang
 */
public interface ApiService {

    @GET("/study/api/dataDictionary/getDateCategory")
    Call<String> getDateCategory(@Query("token") String token, @Query("categoryId") int categoryId);

    @POST("/study/api/user/registered")
    Call<String> regist(@Query("token") String token, @Body Regist userRegistered);

    @POST("/study/api/user/checkUser")
    Call<String> login(@Query("token") String token, @Query("userName") String userName, @Query("password") String password);

    @POST("/study/api/user/updateUserPassWord")
    Call<String> updateUserPassWord(@Query("password") String password, @Query("updatePassword") String updatePassword, @Query("userName") String userName);

    @GET("/study/api/news/findPage")
    Call<String> getNews(@Query("isRelease") boolean isRelease,
                         @Query("newsCategory") int newsCategory,
                         @Query("orderBy") String orderBy,
                         @Query("pageIndex") int pageIndex,
                         @Query("pageSize") int pageSize,
                         @Query("token") String token);

    @GET("/study/api/studyContent/findPage")
    Call<String> getStudys(
            @Query("department") int department,
            @Query("studyContentType") int studyContentType,
            @Query("studyContentCategory") int studyContentCategory,
            @Query("orderBy") String orderBy,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            @Query("token") String token);


    @GET("/study/api/announcement/findPage")
    Call<String> getGongGao(@Query("orderBy") String orderBy,
                            @Query("pageIndex") int pageIndex,
                            @Query("pageSize") int pageSize,
                            @Query("token") String token);

    @GET("/study/api/examinationPaper/findPage")
    Call<String> getShiJuan(@Query("orderBy") String orderBy,
                            @Query("pageIndex") int pageIndex,
                            @Query("pageSize") int pageSize,
                            @Query("token") String token);

    @GET("/study/api/questionBank/selectQuestionBankByPaperId")
    Call<String> getTm(@Query("examinationPaperId") int examinationPaperId,
                       @Query("token") String token);

    @POST("/study/api/examinationRecord/insertExaminationRecord")
    Call<String> pushShiJuan(@Query("duration") long duration,
                             @Query("examinationPaperId") int examinationPaperId,
                             @Query("token") String token,
                             @Query("totalScore") double totalScore,
                             @Query("userId") int userId,
                             @Query("wrongQuestionStr") String wrongQuestionStr);

    @POST("/study/api/studyContentRecord/insertStudyContentRecord")
    Call<String> insertStudyRecord(@Query("creditType") int creditType,
                                   @Query("studyNum") int studyNum,
                                   @Query("duration") long duration,
                                   @Query("studyContentId") int studyContentId,
                                   @Query("token") String token,
                                   @Query("userId") int userId);

    @GET("/study/api/wrongQuestionRecord/findPage")
    Call<String> getWrongTm(@Query("orderBy") String orderBy,
                            @Query("pageIndex") int pageIndex,
                            @Query("pageSize") int pageSize,
                            @Query("token") String token,
                            @Query("userId") int userId);

    @GET("/study/api/studyContentRecord/getStudyRecordLeaderboard")
    Call<String> getPaihang(@Query("token") String token);

    @GET("study/api/examinationRecord/getExaminationRecordSumTotalScore")
    Call<String> getScoreRanking(@Query("token") String token);

    @GET("/study/api/wrongQuestionRecord/deleteByUserId")
    Call<String> clearWrong(@Query("token") String token, @Query("userId") int userId);

    @GET("/study/api/examinationPaper/getExaminationPaperByType")
    Call<String> getExaminationPaperByType(@Query("token") String token, @Query("paperType") int paperType, @Query("userId") int userId);

    @GET("/study/api/studyContentRecord/getPointsLeaderboard")
    Call<String> getPointsLeaderboard(@Query("company") int company,
                                      @Query("department") int department,
                                      @Query("pointsLeaderboardType") int pointsLeaderboardType,
                                      @Query("pageIndex") int pageIndex,
                                      @Query("pageSize") int pageSize,
                                      @Query("token") String token,
                                      @Query("userId") int userId);

    @GET("/study/api/questionBank/findPage")
    Call<String> getTk(@Query("pageIndex") int pageIndex,
                       @Query("pageSize") int pageSize);

    @GET("/study/page/userCreditRule/findPage")
    Call<String> getRule(@Query("pageIndex") int pageIndex,
                         @Query("pageSize") int pageSize);

    @GET("/study/api/userCreditRule/findAllUserCreditRule")
    Call<String> getRule2(@Query("userId") int userId);

    @GET("/study/api/userCreditRecord/findPage")
    Call<String> getScoreDetail(@Query("pageIndex") int pageIndex,
                                @Query("pageSize") int pageSize, @Query("userId") int userId);

    @GET("/study/api/userCreditResult/findUserCreditResultByUserId")
    Call<String> findUserCreditResultByUserId(@Query("userId") int userId);


}
