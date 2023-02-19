package com.ecommerce.app.service;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.ecommerce.app.dto.checkout.CheckoutItemDto;
import com.stripe.Stripe;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Value("${BASE_URL}")
    private static String baseUrl;

    @Value("${STRIPE_SECRET_KEY}")
    private static String apiKey;


    public static Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {

        // success and failure urls

        String successUrl = baseUrl + "payement/success";
        String failureUrl = baseUrl + "payement/failed";

        Stripe.apiKey = "sk_test_51M7KjEIlD5zYNW1x1TQF3KZnRum03nqvrYKHTLr9KwByKyXlJ6nddZWWrtzJPK7KVF8yIiyp20mpGATqy2CnsVDA008CewKXt9";

        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();

        for (CheckoutItemDto checkoutItemDto : checkoutItemDtoList){
            sessionItemList.add(createSessionLineItem(checkoutItemDto));
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureUrl)
                .setSuccessUrl(successUrl)
                .addAllLineItem(sessionItemList)
                .build();

        return Session.create(params);
    }

    private static SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceDate(checkoutItemDto))
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
    }

    private static SessionCreateParams.LineItem.PriceData createPriceDate(CheckoutItemDto checkoutItemDto) {
            return SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("usd")
                    .setUnitAmount((long)checkoutItemDto.getPrice()*100)
                    .setProductData(
                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(checkoutItemDto.getProductName())
                                    .build()
                    ).build();
    }
}
