package com.volkmatrix.website;

import com.volkmatrix.common.enums.Volkservices;
import com.volkmatrix.website.service.model.VolkServices;
import com.volkmatrix.website.service.repo.ServicesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VolkServicesInitializer implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(VolkServicesInitializer.class);
  @Autowired
  private ServicesRepo servicesRepo;

  @Override
  public void run(String... args) throws Exception {


    if (!checkAvailableServices()) {
      createVolkServices();
    }
  }

  private void createVolkServices() {
    log.info("Creating Volk Services");
    List<VolkServices> services = new ArrayList<VolkServices>();

    services.add(new VolkServices(1L, Volkservices.SMS_SERVICE.getValue(), "SMS service"));
    services.add(new VolkServices(2L, Volkservices.VOICE_SERVICE.getValue(), "Voice service"));
    services.add(new VolkServices(3L, Volkservices.WHATSAPP_SERVICE.getValue(), "Whatsapp service"));
    services.add(new VolkServices(4L, Volkservices.EMAIL_SERVICE.getValue(), "Email service"));
    servicesRepo.saveAll(services);

    services.forEach(service -> log.info("Service: {}", service.getName()));

  }

  private boolean checkAvailableServices() {
    log.info("Checking available services");
    List<VolkServices> servicesList = servicesRepo.findAll();
    if (servicesList.isEmpty()) {
      return false;
    }
    return true;
  }
}
