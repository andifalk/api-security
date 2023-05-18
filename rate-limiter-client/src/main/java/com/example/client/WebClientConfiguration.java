package com.example.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl("http://localhost:8080")
                .defaultHeader("Authorization", "Bearer eyJraWQiOiIxYzkyMjUwZi00ODQyLTQzOTgtODRlOC04NDBiODdmYWFkZTQiLCJ0eXAiOiJqd3QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJjNTJiZjdkYi1kYjU1LTRmODktYWM1My04MmI0MGU4YzU3YzIiLCJ3ZWJzaXRlIjoiaHR0cHM6Ly9leGFtcGxlLmNvbSIsInpvbmVpbmZvIjoiRXVyb3BlL0JlcmxpbiIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcm9maWxlIjoiaHR0cHM6Ly9leGFtcGxlLmNvbS9id2F5bmUiLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6OTAwMCIsInByZWZlcnJlZF91c2VybmFtZSI6ImJ3YXluZSIsImdpdmVuX25hbWUiOiJCcnVjZSIsImxvY2FsZSI6ImRlLURFIiwiYXVkIjoiZGVtby1jbGllbnQtcGtjZSIsIm5iZiI6MTY4NDQwNjcyNiwidXBkYXRlZF9hdCI6IjE5NzAtMDEtMDFUMDA6MDA6MDBaIiwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsImVtYWlsIl0sIm5hbWUiOiJCcnVjZSBXYXluZSIsIm5pY2tuYW1lIjoiYndheW5lIiwiZXhwIjoxNjg0NDA3NjI2LCJpYXQiOjE2ODQ0MDY3MjYsImZhbWlseV9uYW1lIjoiV2F5bmUiLCJlbWFpbCI6ImJydWNlLndheW5lQGV4YW1wbGUuY29tIn0.nZhrz1lqycZhqlxuUQcuRWrxpeGCoUPas_05T_9iVj59VnX7UGHUPf_Wdx42CbCmXrr-hV_yqKg6J88HbcunqKcbZ2nezaODXkSMdfn_kF6y1e0F7_V6ndLQY3nUlVrYRY5o-iUPuztAWdcPQzlWuO4bCaPbB5-a6O8toTLE1wJukMej8sk6BPfhjvZiuYPk9a1_Ggi-PHcn3EtStiEn1vh5uWr-vHN1oxdIqNK-YWEcaIVuk4aEcr1EfFZMvjf1tcmJnnYOQkMG4zPoSoVs4sqKcjUSuU1QstYMtnTW-pXmaPDT6CnNs-xX8hYoomNpO7k6Ss2FGsVcsSh5dfKLFA")
                .build();
    }
}
