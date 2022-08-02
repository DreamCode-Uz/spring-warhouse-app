package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springwarhouseapp.entity.Measurement;
import uz.pdp.springwarhouseapp.payload.Result;
import uz.pdp.springwarhouseapp.service.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/api/measurement")
public class MeasurementController {
    private final MeasurementService service;

    @Autowired
    public MeasurementController(MeasurementService service) {
        this.service = service;
    }

    //    CREATE
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Measurement measurement) {
        return service.addMeasurement(measurement);
    }

    //    READ ALL
    @GetMapping
    public ResponseEntity<List<Measurement>> getAll() {
        return service.getAllMeasurements();
    }

    //    READ ONE
    @GetMapping("/id={measurementId}")
    public ResponseEntity<?> getOne(@PathVariable Integer measurementId) {
        return service.getOneMeasurement(measurementId);
    }

    //    UPDATE
    @PutMapping("/id={measurementId}")
    public ResponseEntity<?> update(@PathVariable("measurementId") Integer id, @RequestBody Measurement measurement) {
        return service.editMeasurement(id, measurement);
    }

    //    DELETE
    @DeleteMapping("/id={measurementId}")
    public ResponseEntity<?> delete(@PathVariable("measurementId") Integer id) {
        return service.deleteMeasurement(id);
    }
}
