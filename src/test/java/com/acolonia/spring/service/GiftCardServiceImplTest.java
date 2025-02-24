
package com.acolonia.spring.service;


import com.acolonia.spring.model.GiftCard;
import com.acolonia.spring.repository.GiftCardRepository;
import com.acolonia.spring.service.IServices.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GiftCardServiceImplTest {

        @Mock
        private GiftCardRepository giftCardRepository;


        @Mock
        private EmailService emailService;


        @InjectMocks
        private GiftCardServiceImpl giftCardService;


        private GiftCard giftCard;


        @BeforeEach
        void setUp() {
            giftCard = new GiftCard();
            giftCard.setIdGiftCard(1L);
            giftCard.setCode("GC123");
            giftCard.setAmount(100.0);
        }


        @Test
        void findAllGiftCardsTest() {
            //Debería retornar la lista de tarjetas de regalo
            when(giftCardRepository.findAll()).thenReturn(List.of(giftCard));
            List<GiftCard> giftCardsTest = giftCardService.findAllGiftCards();
            assertFalse(giftCardsTest.isEmpty());
            assertEquals(1, giftCardsTest.size());
            verify(giftCardRepository, times(1)).findAll();
        }


        @Test
        void findByIdGiftCardTest() {
            //Debería retornar la tarjeta de regalo si existe según el ID
            when(giftCardRepository.findById(1L)).thenReturn(Optional.of(giftCard));
            Optional<GiftCard> giftCardsTest = giftCardService.findByIdGiftCard(1L);
            assertTrue(giftCardsTest.isPresent());
            assertEquals(giftCard.getIdGiftCard(), giftCardsTest.get().getIdGiftCard());
            verify(giftCardRepository, times(1)).findById(1L);
        }


        @Test
        void saveNewGiftCardTest() {
            //Debería guardar la tarjeta de regalo cuando es válida
            giftCard.setIdGiftCard(null);
            when(giftCardRepository.save(any(GiftCard.class))).thenReturn(giftCard);
            Optional<GiftCard> savedGiftCard = giftCardService.saveNewGiftCard(giftCard);
            assertTrue(savedGiftCard.isPresent());
            assertEquals(giftCard.getIdGiftCard(), savedGiftCard.get().getIdGiftCard());
            verify(giftCardRepository, times(1)).save(any(GiftCard.class));
        }


        @Test
        void deleteByIdGiftCardTest() {
            //Deberia eliminar una tarjeta de regalo segun su id cuando existe
            when(giftCardRepository.findById(1L)).thenReturn(Optional.of(giftCard));
            doNothing().when(giftCardRepository).deleteById(1L);
            Optional<GiftCard> deletedGiftCard = giftCardService.deleteByIdGiftCard(1L);
            assertTrue(deletedGiftCard.isPresent());
            verify(giftCardRepository, times(1)).deleteById(1L);
        }



}
