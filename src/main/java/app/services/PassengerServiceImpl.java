package app.services;

import app.entities.Passenger;
import app.entities.Passport;
import app.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final int maxVolumePartsOfName = 3;

    private PassengerRepository passengerRepository;
    private PassportService passportService;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, PassportService passportService) {
        this.passengerRepository = passengerRepository;
        this.passportService = passportService;
    }

    @Override
    @Transactional
    public Passenger save(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    @Override
    public List<Passenger> findAll() {
        List<Passenger> passengerList = new ArrayList<>();
        passengerRepository.findAll().forEach(n -> passengerList.add(n));
        return passengerList;
    }

    @Override
    public Passenger findById(Long id) {
        return passengerRepository.findById(id).orElse(null);
    }

    @Override
    public Passenger findByEmail(String email) {
        return findAll().stream()
                .filter(n -> n.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Passenger findBySerialNumberPassport(String serialNumberPassport) {
        Passport passport = passportService.findBySerialNumberPassport(serialNumberPassport).orElse(null);
        if (passport == null) {
            return null;
        }
        return passengerRepository.findByPassportId(passport.getId()).orElse(null);
    }

    @Override
    public List<Passenger> findByFullName(String fullName) {
        String[] separatedName = separationNameOfPassenger(fullName);
        if (separatedName.length > maxVolumePartsOfName || separatedName.length == 0) {
            return null;
        }
        return filterListPassenger(passengerRepository.findByLastName(separatedName[0]), separatedName);
    }

    @Override
    public List<Passenger> findByLastName(String lastName) {
        return passengerRepository.findByLastName(lastName);
    }

    @Override
    public List<Passenger> findByAnyName(String anyName) {
        String[] partsOfNameName = separationNameOfPassenger(anyName);
        if (partsOfNameName.length > maxVolumePartsOfName || partsOfNameName.length == 0) {
            return null;
        }
        return findPassengersByAnyName(partsOfNameName);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!passengerRepository.findById(id).isPresent()) {
            return;
        }
        passengerRepository.deleteById(id);
    }

    private List<Passenger> findPassengersByAnyName(String[] partsOfName) {
        List<Passenger> passengerListByLastName = passengerRepository.findByLastName(partsOfName[0]);
        List<Passenger> passengerListByFirstName = passengerRepository.findByFirstName(partsOfName[0]);
        List<Passenger> passengerListByMiddleName = passengerRepository.findByMiddleName(partsOfName[0]);

        if (passengerListByLastName == null && passengerListByFirstName == null && passengerListByMiddleName == null) {
            return null;
        } else if (passengerListByLastName != null && passengerListByFirstName == null && passengerListByMiddleName == null) {
            return filterListPassenger(passengerListByLastName, partsOfName);
        } else if (passengerListByFirstName != null && passengerListByLastName == null && passengerListByMiddleName == null) {
            return filterListPassenger(passengerListByFirstName, partsOfName);
        } else if (passengerListByMiddleName != null && passengerListByLastName == null && passengerListByFirstName == null) {
            return filterListPassenger(passengerListByMiddleName, partsOfName);
        } else {
            Set<Passenger> passengersSet = new HashSet<>();
            passengersSet.addAll(passengerListByLastName);
            passengersSet.addAll(passengerListByFirstName);
            passengersSet.addAll(passengerListByMiddleName);
            return filterListPassenger(passengersSet.stream().collect(Collectors.toList()), partsOfName);
        }
    }

    private String[] separationNameOfPassenger(String name) {
        return name.split("\\s+");
    }

    private List<Passenger> filterListPassenger(List<Passenger> passengerList, String[] partsOfName) {
        for (int i = 0; i < partsOfName.length; i++) {
            for (int j = 0; j < passengerList.size(); j++) {
                if (!passengerList.get(j).getLastName().equals(partsOfName[i]) &&
                        !passengerList.get(j).getFirstName().equals(partsOfName[i]) &&
                        !passengerList.get(j).getMiddleName().equals(partsOfName[i])) {
                    passengerList.remove(j);
                }
            }
        }
        return passengerList;
    }


}