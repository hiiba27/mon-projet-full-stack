package com.fst.rendement.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fst.rendement.services.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth

                // 🔓 Auth publique
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()

                /* ===================== EMPLOYÉS ===================== */
                // 📄 Liste employés : ADMIN + CHEF_EQUIPE
                .requestMatchers(HttpMethod.GET, "/api/employees/**")
                    .hasAnyRole("ADMINISTRATEUR", "CHEF_EQUIPE")

                // ➕✏️❌ Ajout / modif / suppression employés : ADMIN uniquement
                .requestMatchers(HttpMethod.POST, "/api/employees/**")
                    .hasRole("ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PUT, "/api/employees/**")
                    .hasRole("ADMINISTRATEUR")
                .requestMatchers(HttpMethod.DELETE, "/api/employees/**")
                    .hasRole("ADMINISTRATEUR")

                /* ===================== MACHINES / PRODUITS ===================== */
                // ⚙️ CRUD complet : ADMIN + CHEF_EQUIPE
                .requestMatchers("/api/machines/**")
                    .hasAnyRole("ADMINISTRATEUR", "CHEF_EQUIPE")
                .requestMatchers("/api/produits/**")
                    .hasAnyRole("ADMINISTRATEUR", "CHEF_EQUIPE")
                .requestMatchers("/api/productions/**")
                    .hasAnyRole("ADMINISTRATEUR", "CHEF_EQUIPE")
                .requestMatchers("/api/commandes/**")
                    .hasAnyRole("ADMINISTRATEUR", "CHEF_EQUIPE")

                /* ===================== RENDEMENTS & RAPPORTS ===================== */
                // 📊 ADMIN uniquement
                .requestMatchers("/api/rendements/**")
                    .hasRole("ADMINISTRATEUR")
                .requestMatchers("/api/rapports/**")
                    .hasRole("ADMINISTRATEUR")

                /* ===================== AUTRES ===================== */
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .httpBasic();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
