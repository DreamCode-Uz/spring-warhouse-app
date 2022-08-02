package uz.pdp.springwarhouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springwarhouseapp.entity.Measurement;
import uz.pdp.springwarhouseapp.payload.Result;
import uz.pdp.springwarhouseapp.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class MeasurementService {
    private final MeasurementRepository repository;

    @Autowired
    public MeasurementService(MeasurementRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> addMeasurement(Measurement measurement) {
        if (repository.existsByName(measurement.getName())) return new ResponseEntity<>("Measurement already exist.", ALREADY_REPORTED);
        return new ResponseEntity<>(repository.save(measurement), CREATED);
    }

    public ResponseEntity<List<Measurement>> getAllMeasurements() {
        return new ResponseEntity<>(repository.findAll(), OK);
    }

    public ResponseEntity<?> getOneMeasurement(Integer id) {
        Optional<Measurement> optionalMeasurement = repository.findById(id);
        if (!optionalMeasurement.isPresent()) return new ResponseEntity<>(NOT_FOUND);
        return new ResponseEntity<>(optionalMeasurement.get(), OK);
    }

    public ResponseEntity<?> editMeasurement(Integer id, Measurement measurement) {
        Optional<Measurement> optionalMeasurement = repository.findById(id);
        if (optionalMeasurement.isPresent()) {
            Measurement result = optionalMeasurement.get();
            if (!repository.existsByName(measurement.getName())) {
                result.setName(measurement.getName());
            }
            result.setActive(measurement.isActive());
            return new ResponseEntity<>(repository.save(result), OK);
        }
        return new ResponseEntity<>("Measurement not found.", NOT_FOUND);
    }

    public ResponseEntity<?> deleteMeasurement(Integer measurementId) {
        Optional<Measurement> optionalMeasurement = repository.findById(measurementId);
        if (optionalMeasurement.isPresent()) {
            try {
                repository.delete(optionalMeasurement.get());
                return new ResponseEntity<>("Measurement successfully deleted.", OK);
            } catch (Exception e) {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Measurement not found.", NOT_FOUND);
    }
}
