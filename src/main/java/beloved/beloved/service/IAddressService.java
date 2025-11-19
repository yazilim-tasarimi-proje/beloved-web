package beloved.beloved.service;

import beloved.beloved.dto.AddressUpdateRequest;

import java.util.List;

public interface IAddressService {
    AddressUpdateRequest addAddress(AddressUpdateRequest request, String userEmail);
    AddressUpdateRequest updateAddress(AddressUpdateRequest request, String userEmail);
    void deleteAddress(Long addressId, String userEmail);
    List<AddressUpdateRequest> getUserAddresses(String userEmail);
}
