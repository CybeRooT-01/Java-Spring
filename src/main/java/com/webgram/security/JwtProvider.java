package com.webgram.security;


import com.webgram.service.IAuthService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
- JwtProvider est une classe util -> il implémente des fonctions utiles:
générer un jeton JWT
valider un jeton JWT
analyser le nom d' utilisateur du jeton JWT
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final IAuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    private String jwtSecret = "scretKetGainde2k2021scretKetGainde2k2021xxscretKetGainde2k2021";

    @Value("3600")
    private int jwtExpiration;

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("La signature du JWT est invalide -> Message: {}");
            //logger.error("Signature JWT non valide -> Message: {}", e);
        } catch (MalformedJwtException e) {
            logger.error("Le format du JWT est incorrect -> Message: {}");
            //logger.error("Jeton JWT non valide -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Le jeton JWT a expiré -> Message: {}");
            //logger.error("Jeton JWT expiré -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Le format du jeton JWT n'est pris en charge -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("La chaîne de réclamation JWT est vide -> Message: {}", e);
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
