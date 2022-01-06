package com.sgmtec.SpringBootStudy.service;

import com.sgmtec.SpringBootStudy.dto.MessageResponseDTO;
import com.sgmtec.SpringBootStudy.dto.PersonDTO;
import com.sgmtec.SpringBootStudy.entity.Person;
import com.sgmtec.SpringBootStudy.exception.PersonNotFoundException;
import com.sgmtec.SpringBootStudy.mapper.PersonMapper;
import com.sgmtec.SpringBootStudy.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonMapper personMapper = PersonMapper.INSTANCE;
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){

        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO){
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created person with ID ");
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }
     public PersonDTO findById(Long id) throws PersonNotFoundException {
       //  Optional<Person> optionalPerson = personRepository.findById(id);
       //  if(optionalPerson.isEmpty()){
       //      throw new PersonNotFoundException(id);
       //  }
       //  return personMapper.toDTO(optionalPerson.get());
         Person person = verifyIfExists(id);
         return personMapper.toDTO(person);
    }
    public void deleteById(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);
        Person personToUpdate = personMapper.toModel(personDTO);
        Person updatePerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatePerson.getId() ," Update person with ID ");
    }

    private MessageResponseDTO createMessageResponse(long id, String s) {
        return MessageResponseDTO
                .builder()
                .message(s + id)
                .build();
    }
    private Person verifyIfExists(Long id) throws PersonNotFoundException{
        return personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id));
    }
}
