package com.sanaesencia.backend.config;

import com.sanaesencia.backend.entity.*;
import com.sanaesencia.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AboutInfoRepository aboutInfoRepository;
    private final BoxRepository boxRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void run(String... args) {
        log.info("Verificando datos iniciales del sistema...");

        // 1. Información Institucional
        if (aboutInfoRepository.count() == 0) {
            aboutInfoRepository.save(AboutInfo.builder()
                    .id(1)
                    .centerTitle("Centro Integrativo Sanaesencia")
                    .description("Salud mental infanto-juvenil, familia y bienestar integrativo. Atendemos de forma presencial en nuestros 8 boxes de Los Vilos y online para todo Chile y el extranjero.")
                    .address("Caupolicán 220, Los Vilos")
                    .phone("+56 9 2366 5073")
                    .email("contacto@centrosanaesencia.cl")
                    .build());
            log.info("Información institucional sembrada correctamente.");
        }

        // 2. Boxes de Atención (1 al 8)
        if (boxRepository.count() == 0) {
            boxRepository.saveAll(List.of(
                    Box.builder().boxNumber(1).name("Box 1 - Medicina General").status("disponible").description("Box equipado para evaluación clínica integral").build(),
                    Box.builder().boxNumber(2).name("Box 2 - Fonoaudiología").status("disponible").description("Box acústico para terapia fonoaudiológica").build(),
                    Box.builder().boxNumber(3).name("Box 3 - Psicología Infantil").status("disponible").description("Box lúdico para terapia infanto-juvenil").build(),
                    Box.builder().boxNumber(4).name("Box 4 - Psicología Adultos").status("disponible").description("Espacio terapéutico para salud mental").build(),
                    Box.builder().boxNumber(5).name("Box 5 - Terapia Ocupacional").status("disponible").description("Box con equipamiento sensorial motor").build(),
                    Box.builder().boxNumber(6).name("Box 6 - Nutrición y Bienestar").status("disponible").description("Box de evaluación nutricional").build(),
                    Box.builder().boxNumber(7).name("Box 7 - Terapias Florales").status("disponible").description("Espacio para terapias holísticas y bienestar").build(),
                    Box.builder().boxNumber(8).name("Box 8 - Consulta Multidisciplinaria").status("disponible").description("Box de atención familiar y consultas compartidas").build()
            ));
            log.info("Los 8 boxes de atención sembrados correctamente.");
        }

        // 3. Usuarios Iniciales
        if (userRepository.count() == 0) {
            userRepository.saveAll(List.of(
                    User.builder()
                            .run("admin")
                            .name("Administrador Centro Sanaesencia")
                            .email("admin@sanaesencia.cl")
                            .phone("+56923665073")
                            .passwordHash("$2a$10$4CXqaQqpEDrzryJuEeBJRODdzAqT0V/hDGb1zBKTOuXpmTMk.n9/.") // admin
                            .role("ADMIN")
                            .specialty("Gestión Clínica")
                            .build(),
                    User.builder()
                            .run("11111111-1")
                            .name("Administrador Sanaesencia (RUT)")
                            .email("admin.rut@sanaesencia.cl")
                            .phone("+56923665073")
                            .passwordHash("$2a$10$5qQhZ5xDHIG0Y0vfHZZU5.RiM43ou33Xa.QtIYzlnDpwelng.OzMC")
                            .role("ADMIN")
                            .specialty("Gestión Clínica")
                            .build(),
                    User.builder()
                            .run("20000001-3")
                            .name("Dra. Keilen Sánchez Ríos")
                            .email("keilen.sanchez@sanaesencia.cl")
                            .phone("+56923665073")
                            .passwordHash("$2a$10$X1KAlbCLRFpEzBCadDTPg.Fp.yqhEc2PudK7JZakAuY9ISrAK/YIG") // 1234
                            .role("PROFESIONAL")
                            .specialty("Medicina General y Salud Mental")
                            .build(),
                    User.builder()
                            .run("15234567-8")
                            .name("Ps. Rodrigo Morales")
                            .email("rmorales@sanaesencia.cl")
                            .phone("+56912345678")
                            .passwordHash("$2a$10$KnoTGcvA1U2BhvMgrIi7Mu254HdudzFdLUI17MoPMx.zOPgPYjLzm")
                            .role("PROFESIONAL")
                            .specialty("Psicología Infanto-Juvenil")
                            .build(),
                    User.builder()
                            .run("16345678-9")
                            .name("Flga. Camila Vega")
                            .email("cvega@sanaesencia.cl")
                            .phone("+56987654321")
                            .passwordHash("$2a$10$KnoTGcvA1U2BhvMgrIi7Mu254HdudzFdLUI17MoPMx.zOPgPYjLzm")
                            .role("PROFESIONAL")
                            .specialty("Fonoaudiología")
                            .build(),
                    User.builder()
                            .run("17456789-0")
                            .name("Nut. Valentina Araya")
                            .email("varaya@sanaesencia.cl")
                            .phone("+56976543210")
                            .passwordHash("$2a$10$KnoTGcvA1U2BhvMgrIi7Mu254HdudzFdLUI17MoPMx.zOPgPYjLzm")
                            .role("PROFESIONAL")
                            .specialty("Nutrición Integrativa")
                            .build(),
                    User.builder()
                            .run("20000100-1")
                            .name("Paciente de Prueba")
                            .email("paciente@correo.com")
                            .phone("+56987654321")
                            .passwordHash("$2a$10$X1KAlbCLRFpEzBCadDTPg.Fp.yqhEc2PudK7JZakAuY9ISrAK/YIG") // 1234
                            .role("PACIENTE")
                            .build(),
                    User.builder()
                            .run("18567890-1")
                            .name("Constanza Silva")
                            .email("paciente@test.cl")
                            .phone("+56911223344")
                            .passwordHash("$2a$10$KnoTGcvA1U2BhvMgrIi7Mu254HdudzFdLUI17MoPMx.zOPgPYjLzm")
                            .role("PACIENTE")
                            .build()
            ));
            log.info("Usuarios iniciales sembrados correctamente.");
        }

        // 4. Catálogo de Productos
        if (productRepository.count() == 0) {
            productRepository.saveAll(List.of(
                    Product.builder()
                            .name("Aceite Esencial Lavanda Francesa (15ml)")
                            .description("100% puro para relajación del sistema nervioso y mejora del descanso nocturno.")
                            .price(new BigDecimal("12990.00"))
                            .isAvailable(true)
                            .imageUrl("https://centrosanaesencia.cl/img/lavanda.jpg")
                            .stock(25)
                            .build(),
                    Product.builder()
                            .name("Jabón Artesanal de Ruda y Romero")
                            .description("Elaborado con aceites vegetales nutritivos y hierbas recolectadas del valle.")
                            .price(new BigDecimal("4500.00"))
                            .isAvailable(true)
                            .imageUrl("https://centrosanaesencia.cl/img/jabon_ruda.jpg")
                            .stock(40)
                            .build(),
                    Product.builder()
                            .name("Infusión Herbal Digestiva & Calmante (80g)")
                            .description("Mezcla sinérgica de melisa, manzanilla, menta negra y cedrón orgánico.")
                            .price(new BigDecimal("6200.00"))
                            .isAvailable(true)
                            .imageUrl("https://centrosanaesencia.cl/img/infusion_calmante.jpg")
                            .stock(30)
                            .build(),
                    Product.builder()
                            .name("Bruma Áurica Armonizadora (100ml)")
                            .description("Spray ambiental con esencias florales y aceites cítricos para limpiar espacios.")
                            .price(new BigDecimal("8900.00"))
                            .isAvailable(true)
                            .imageUrl("https://centrosanaesencia.cl/img/bruma.jpg")
                            .stock(15)
                            .build(),
                    Product.builder()
                            .name("Roll-on Anti-Estrés y Foco (10ml)")
                            .description("Aplicador de aromaterapia con bergamota, incienso y jojoba para sienes y muñecas.")
                            .price(new BigDecimal("7500.00"))
                            .isAvailable(true)
                            .imageUrl("https://centrosanaesencia.cl/img/rollon.jpg")
                            .stock(50)
                            .build()
            ));
            log.info("Catálogo de productos sembrado correctamente.");
        }
    }
}
