package beloved.beloved.controller;

import beloved.beloved.dto.AddressUpdateRequest;
import beloved.beloved.service.IAddressService;
import beloved.beloved.service.impl.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final IAddressService addressService;
    private final JWTUtil jwtUtil;

    public AddressController(IAddressService addressService, JWTUtil jwtUtil) {
        this.addressService = addressService;
        this.jwtUtil = jwtUtil;
    }

    private String getEmailFromRequest(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        return jwtUtil.extractUsername(token);
    }

    @PostMapping
    public ResponseEntity<AddressUpdateRequest> addAddress(@RequestBody AddressUpdateRequest request, HttpServletRequest httpRequest) {
        String email = getEmailFromRequest(httpRequest);
        return ResponseEntity.ok(addressService.addAddress(request, email));
    }

    @PutMapping
    public ResponseEntity<AddressUpdateRequest> updateAddress(@RequestBody AddressUpdateRequest request, HttpServletRequest httpRequest) {
        String email = getEmailFromRequest(httpRequest);
        return ResponseEntity.ok(addressService.updateAddress(request, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id, HttpServletRequest httpRequest) {
        String email = getEmailFromRequest(httpRequest);
        addressService.deleteAddress(id, email);
        return ResponseEntity.ok("Address deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<AddressUpdateRequest>> getUserAddresses(HttpServletRequest httpRequest) {
        String email = getEmailFromRequest(httpRequest);
        return ResponseEntity.ok(addressService.getUserAddresses(email));
    }
}
