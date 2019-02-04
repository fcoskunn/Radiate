package com.fc.radiate.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.fc.radiate.DataManagement.*;


import java.util.List;

public interface ApiService {
    @GET("countries")
    Call<List<Country>> getCountries();

    @GET("languages")
    Call<List<Language>> getLanguages();

    @GET("stations/{url}")
    Call<List<Station>> getStations(@Path("url") String url);

    @GET("stations/topclick/{num}")
    Call<List<Station>> getTopclickStations(@Path("num") String num);

    @GET("stations/bycountryexact/{country}")
    Call<List<Station>> getStationByCountry(@Path("country") String country,
                                            @Query("order") String order,
                                            @Query("reverse") boolean reverse);

    @GET("stations/search")
    Call<List<Station>> getSearchResults(@Query("name") String name,
                                        @Query("country") String country,
                                        @Query("language") String language,
                                        @Query("tags") String tags,
                                        @Query("limit") String limit,
                                        @Query("order") String orderBy,
                                        @Query("reverse") boolean reverse,
                                        @Query("offset") int offset);

}
