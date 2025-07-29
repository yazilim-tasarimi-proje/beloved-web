package beloved.beloved.service.impl;

import beloved.beloved.dto.AddressUpdateRequest;
import beloved.beloved.entity.Address;
import beloved.beloved.entity.User;
import beloved.beloved.repository.AddressRepository;
import beloved.beloved.repository.UserRepository;
import beloved.beloved.service.IAddressService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService implements IAddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    private Address dtoToEntity(AddressUpdateRequest dto, User user) {
        Address address = new Address();
        address.setId(dto.getAddressId());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setPhone(dto.getPhone());
        address.setUser(user);
        return address;
    }

    private AddressUpdateRequest entityToDto(Address address) {
        return new AddressUpdateRequest(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry(),
                address.getPhone()
        );
    }

    @Override
    public AddressUpdateRequest addAddress(AddressUpdateRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Address saved = addressRepository.save(dtoToEntity(request, user));
        return entityToDto(saved);
    }

    @Override
    public AddressUpdateRequest updateAddress(AddressUpdateRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You are not allowed to update this address");
        }

        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setPhone(request.getPhone());

        Address updated = addressRepository.save(address);
        return entityToDto(updated);
    }

    @Override
    public void deleteAddress(Long addressId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You are not allowed to delete this address");
        }

        addressRepository.delete(address);
    }

    @Override
    public List<AddressUpdateRequest> getUserAddresses(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return addressRepository.findAllByUser(user).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
