package com.turkcell.turkcellcrm.customerService.dataAccess;

import com.turkcell.turkcellcrm.customerService.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Integer> {

    Optional<Address> findByCustomerId(int id);
    Optional<Address> findAddressById(int id);
    List<Address> findByDeletedDateIsNull();
}
