package com.neelhpatel.spoileralert;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BarcodeService {
    @GET("product/{barcode}/{apiKey}")
    Call<UPCItem> lookupBarcode(@Path("barcode") String barcode, @Path("apiKey") String apiKey);
}
