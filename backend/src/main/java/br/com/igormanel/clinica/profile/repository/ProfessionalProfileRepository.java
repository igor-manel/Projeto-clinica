package br.com.igormanel.clinica.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.igormanel.clinica.profile.entity.ProfessionalProfile;

public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfile, Integer> {
}
