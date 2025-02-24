package com.acolonia.spring.service.IServices;

import com.acolonia.spring.model.GiftCard;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface GiftCardService {

    //Método para el manejo de la lógica del Crud de Gift Cards
    List<GiftCard> findAllGiftCards();

    Optional<GiftCard> findByIdGiftCard(Long id);

    Optional<GiftCard> saveNewGiftCard (GiftCard giftCard);

    Optional<GiftCard> deleteByIdGiftCard(Long id);

    Optional<GiftCard> updateGiftcard (Long id, GiftCard updateGiftcard);

    Optional<GiftCard> redeemGiftCard (String code);

}
