package com.smartfusiontech.cards.service;
import com.smartfusiontech.cards.entity.Address;
import com.smartfusiontech.cards.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AddressService {
  @Autowired
  private AddressRepository addressRepository;
  public Address AddressService(Address address) {
   Address dbobj =this.addressRepository.save(address);
   return dbobj;
  }
}
