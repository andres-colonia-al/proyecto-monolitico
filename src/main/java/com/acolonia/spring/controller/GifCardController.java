package com.acolonia.spring.controller;

import com.acolonia.spring.model.GiftCard;
import com.acolonia.spring.service.IServices.GiftCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/giftcard")
@Tag(name = "GifCard", description = "API para gestionar las GifCards")
public class GifCardController {

    @Autowired
    private GiftCardService giftCardService;


    @GetMapping
    @Operation(summary = "Obtener todas las Gifcards", description = "Retorna una lista con todas las tarjetas de regalo registradas")
    public ResponseEntity<List<GiftCard>> getAllGiftCards() {
        return new ResponseEntity<>(giftCardService.findAllGiftCards(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener la Gifcard según el id", description = "Retorna un objeto GifCard según su id")
    public ResponseEntity<GiftCard> getGiftCardById (@PathVariable Long id){
        return giftCardService.findByIdGiftCard(id)
                .map(giftCard -> new ResponseEntity<>(giftCard, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Registra una nueva GifCard en la base de datos", description = "Retorna la nueva tarjeta registrada no enviar idGiftCard en el Request body.")
    public ResponseEntity<Optional<GiftCard>> createGiftCard (@RequestBody GiftCard giftCard) {
        Optional<GiftCard> savedGiftCard = giftCardService.saveNewGiftCard(giftCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGiftCard);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una GifCard de la base de datos", description = "Actualiza la Gifcard solo en los campos (monto, status y fecha de expiración) no enviar idGiftCard en el Request body.")
    public ResponseEntity<Optional<GiftCard>> updateGiftCard (@PathVariable Long id, @RequestBody GiftCard updateGiftCard) {
        Optional<GiftCard> updatedGiftCard = giftCardService.updateGiftcard(id, updateGiftCard);
        return ResponseEntity.status(HttpStatus.OK).body(updatedGiftCard);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una Gifcard en la base de datos segun el id", description = "Elimina la GifCard de la base de datos según el id")
    public ResponseEntity<Void> deleteGiftCard(@PathVariable Long id) {
        Optional<GiftCard> deletedGiftCard = giftCardService.deleteByIdGiftCard(id);
        if (deletedGiftCard.isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/redeem/{code}")
    @Operation(summary = "Permite redimir las GiftCards pasando su codigo_unico", description = "Actualiza el estado a redimido en la base de datos y envía notificación al correo electrónico")
    public ResponseEntity<String> redeemGiftCard(@PathVariable String code) {
        Optional<GiftCard> redeemedGiftCard = giftCardService.redeemGiftCard(code);
        if (redeemedGiftCard.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La tarjeta no se encontró, ya fue redimida o ha expirado");
        }
        return ResponseEntity.ok("Tarjeta redimida exitosamente");
    }

}
