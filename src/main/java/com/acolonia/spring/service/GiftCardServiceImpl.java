package com.acolonia.spring.service;

import com.acolonia.spring.model.GiftCard;
import com.acolonia.spring.model.enums.GiftcardStatus;
import com.acolonia.spring.repository.GiftCardRepository;
import com.acolonia.spring.service.IServices.EmailService;
import com.acolonia.spring.service.IServices.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCardServiceImpl implements GiftCardService {

    //Inyección de servicios
    @Autowired
    private GiftCardRepository giftCardRepository;

    @Autowired
    private EmailService emailService;

    @Value("${email.notifications}")
    private String emailNotifications;

    //Sobrescritura de métodos del GiftCardService
    @Override
    @Transactional(readOnly = true)
    public List<GiftCard> findAllGiftCards() {
        return giftCardRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GiftCard> findByIdGiftCard(Long id) {
        return giftCardRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<GiftCard> saveNewGiftCard(GiftCard giftCard) {
        if (giftCard.getIdGiftCard() == null || giftCard.getIdGiftCard() == 0) {
            GiftCard giftCardOptional = giftCardRepository.save(giftCard);
            //Imprime por Consola
            System.out.println("Se ha creado tarjeta con éxito: " + giftCardOptional.getCode());
            //Notificación por Correo electrónico de creación de tarjeta
            emailService.sendEmail(
                    emailNotifications,
                    "Creación de GiftCard",
                    "Se ha creado una nueva tarjeta con éxito: \n" + giftCardOptional);

            return Optional.of(giftCardOptional);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<GiftCard> deleteByIdGiftCard(Long id) {
        Optional<GiftCard> giftCardOptional = giftCardRepository.findById(id);
        if (giftCardOptional.isPresent()) {
            giftCardRepository.deleteById(id);
            return giftCardOptional;
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<GiftCard> updateGiftcard(Long id, GiftCard updateGiftcard) {
        return giftCardRepository.findById(id)
                .map(existingGiftCard -> {
                    // Solo actualiza los valores si no son nulos en el objeto recibido
                    if (updateGiftcard.getAmount() != null) {
                        existingGiftCard.setAmount(updateGiftcard.getAmount());
                    }
                    if (updateGiftcard.getStatus() != null) {
                        existingGiftCard.setStatus(updateGiftcard.getStatus());
                    }
                    if (updateGiftcard.getExpiresAt() != null) {
                        existingGiftCard.setExpiresAt(updateGiftcard.getExpiresAt());
                    }
                    return giftCardRepository.save(existingGiftCard);
                });
    }

    @Override
    @Transactional
    public Optional<GiftCard> redeemGiftCard(String code) {
        Optional<GiftCard> giftCardOptional = giftCardRepository.findByCode(code);

        if (giftCardOptional.isEmpty()) {
            return Optional.empty();
        }

        GiftCard giftCard = giftCardOptional.get();
        if (giftCard.getStatus() == GiftcardStatus.REDIMIDA) {
            return Optional.empty();
        }

        if (giftCard.getStatus() == GiftcardStatus.EXPIRADA) {
            return Optional.empty();
        }

        giftCard.setStatus(GiftcardStatus.REDIMIDA);
        giftCardRepository.save(giftCard);

        //Envió de correo electrónico
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        emailService.sendEmail(
                emailNotifications,
                "Consumo de Gift Card N° " + giftCard.getCode() ,
                "¡Felicitaciones! Se ha redimido la tarjeta correctamente: \n\n " + giftCard.getCode() + " El día " + LocalDateTime.now().format(formatter));
        //Impresión por consola
        System.out.println("Tarjeta redimida: " + giftCard.getCode());

        return Optional.of(giftCard);
    }
}
