package com.webgram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webgram.entities.Profil;
import com.webgram.entities.Utilisateur;
import com.webgram.security.JwtTokenUtil;
import com.webgram.service.IAuthService;
import com.webgram.service.IUtilisateurService;
import com.webgram.service.JwtUserDetailsService;
import com.webgram.utils.JwtResponse;
import com.webgram.utils.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final IAuthService iAuthService;
    private final JwtTokenUtil jwtTokenUtil;
    private final IUtilisateurService utilisateurService;


    @GetMapping(value = "/password")
    public Response<?> encodePassord() {
        return Response.ok().setData(passwordEncoder.encode("passer"));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<?> authenticate(@RequestBody sn.gainde2000.dii.models.LoginForm authenticationRequest, HttpServletResponse response) throws Exception {
        try {
            @SuppressWarnings("unused")
            Authentication authenticate = authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(authenticationRequest.getEmail());

            if (iAuthService.getConnectedUser().isNewConnexion()) {
                String accessToken = jwtTokenUtil.generateToken(userDetails);
                return Response.firstConnexion().setData(JwtResponse.builder()
                        .accessToken(accessToken)
                        .username(userDetails.getUsername())
                        .authorities(userDetails.getAuthorities()).build());
            }

            if (!iAuthService.getConnectedUser().getStatut()) {
                return Response.disabledAccount().setErrors("Votre compte est désactivé, veuillez contacter l'administrateur.");
            }

            String accessToken = jwtTokenUtil.generateToken(userDetails);

            return Response.ok().setData(JwtResponse.builder()
                    .accessToken(accessToken)
                    .username(userDetails.getUsername())
                    .authorities(userDetails.getAuthorities())
                    .build());
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
            return Response.wrongCredentials().setErrors("Identifiant et/ou mot de passe incorrects. Merci de ressayer");
        }
    }

    @RequestMapping(value = "/inscription", method = RequestMethod.POST)
    public Response<?> inscriptionTemporaire(@RequestPart("utilisateur") String utilisateur1) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Utilisateur utilisateur = objectMapper.readValue(utilisateur1, Utilisateur.class);
            utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
            utilisateur.setDateCreation(new Date());
            utilisateur.setNewConnexion(false);
            utilisateur.setProfil(new Profil((long) 2, "Demandeur", "Demandeur"));
            utilisateurService.save(utilisateur);
            return Response.ok().setData(utilisateur).setMessage("Utilisateur enregistré avec success");

        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
            return Response.wrongCredentials().setErrors("Identifiant et/ou mot de passe incorrects. Merci de ressayer");
        }
    }

    @RequestMapping(value = "/user/inscription", method = RequestMethod.POST)
    public Response<?> inscription(@RequestPart("utilisateur") String user, @RequestPart("numero") String numero) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Utilisateur utilisateur = objectMapper.readValue(user, Utilisateur.class);
            String numero1 = objectMapper.readValue(numero, String.class);
            utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
            utilisateur.setDateCreation(new Date());
            utilisateur.setNewConnexion(false);

            utilisateur.setProfil(new Profil((long) 2, "User", "User"));
            utilisateurService.save(utilisateur);

            return Response.ok().setData(utilisateur).setMessage("Utilisateur enregistré avec success");

        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
            return Response.wrongCredentials().setErrors(e.getLocalizedMessage());
        }
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public Response<?> editProfi(@RequestPart("utilisateur") String user) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Utilisateur utilisateur = objectMapper.readValue(user, Utilisateur.class);
            Utilisateur u = iAuthService.findUtilisateurById(utilisateur.getId());
            u.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
            u.setPrenom(utilisateur.getPrenom());
            u.setNom(utilisateur.getNom());
            utilisateurService.save(utilisateur);
            return Response.ok().setData(utilisateur).setMessage("Utilisateur modifié avec success");

        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
            return Response.wrongCredentials().setErrors(e.getLocalizedMessage());
        }
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public Response<?> getAllUser() {
        List<Utilisateur> utilisateurList = utilisateurService.listeUtilisateurs();
        return utilisateurList == null ? Response.exception().setErrors("Une erreur est survenue") : Response.ok().setData(utilisateurList).setMessage("Liste des Utilisateurs");
    }

    @RequestMapping(value = "/user/statistique", method = RequestMethod.GET)
    public Response<?> getUserStatistique() {
        List<Utilisateur> utilisateurList = utilisateurService.listeUtilisateurs();
        HashMap<String, Integer> stat = new HashMap<>();
        int all = utilisateurList.size();
        int active = 0;
        int desactive = 0;
        for (Utilisateur u : utilisateurList) {
            if (u.getStatut()) {
                active++;
            } else {
                desactive++;
            }
        }
        stat.put("Tous", all);
        stat.put("Active", active);
        stat.put("Desactive", desactive);
        return Response.ok().setData(stat).setMessage("Statistique des Utilisateurs");
    }

    @GetMapping(value = "/user/update/{mail}")
    public Response<?> activeDesactiveUser(@PathVariable(name = "mail") String mail) {
        Utilisateur utilisateur = utilisateurService.findByEmail(mail);
        utilisateur.setNewConnexion(!utilisateur.isNewConnexion());
        utilisateurService.save(utilisateur);
        return Response.ok().setData(utilisateur).setMessage("User Updated");
    }
}
