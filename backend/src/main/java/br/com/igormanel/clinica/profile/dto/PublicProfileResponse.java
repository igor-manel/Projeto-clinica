package br.com.igormanel.clinica.profile.dto;

import br.com.igormanel.clinica.profile.entity.Address;
import br.com.igormanel.clinica.profile.entity.ProfessionalProfile;

/**
 * Dados exibidos na área pública. Valores nulos indicam informação ainda não
 * fornecida; o frontend mantém o placeholder correspondente nesses casos.
 */
public record PublicProfileResponse(
		String name,
		String professionalRegistry,
		String education,
		String approach,
		String onlineServiceInfo,
		Contact contact,
		Location location) {

	public record Contact(Channel whatsapp, Channel email) {
	}

	/** Canal de contato: texto para exibição e link correspondente. */
	public record Channel(String display, String url) {
	}

	public record Location(
			String street,
			String complement,
			String district,
			String city,
			String state,
			String postalCode,
			String accessInfo) {
	}

	public static PublicProfileResponse from(ProfessionalProfile profile) {
		return new PublicProfileResponse(
				profile.getDisplayName(),
				profile.getProfessionalRegistry(),
				profile.getEducation(),
				profile.getApproach(),
				profile.getOnlineServiceInfo(),
				new Contact(whatsapp(profile.getWhatsappNumber()), email(profile.getEmail())),
				location(profile.getAddress()));
	}

	private static Channel whatsapp(String digits) {
		if (digits == null) {
			return null;
		}
		return new Channel(formatPhone(digits), "https://wa.me/" + digits);
	}

	private static Channel email(String address) {
		if (address == null) {
			return null;
		}
		return new Channel(address, "mailto:" + address);
	}

	private static Location location(Address address) {
		if (address == null) {
			return new Location(null, null, null, null, null, null, null);
		}
		return new Location(
				address.getStreet(),
				address.getComplement(),
				address.getDistrict(),
				address.getCity(),
				address.getState(),
				formatPostalCode(address.getPostalCode()),
				address.getAccessInfo());
	}

	/** Formata números brasileiros (55 + DDD + 8 ou 9 dígitos); demais ficam como +DDI... */
	static String formatPhone(String digits) {
		if (digits.startsWith("55") && (digits.length() == 12 || digits.length() == 13)) {
			String areaCode = digits.substring(2, 4);
			String local = digits.substring(4);
			int split = local.length() - 4;
			return "+55 (" + areaCode + ") " + local.substring(0, split) + "-" + local.substring(split);
		}
		return "+" + digits;
	}

	static String formatPostalCode(String digits) {
		if (digits == null) {
			return null;
		}
		return digits.substring(0, 5) + "-" + digits.substring(5);
	}

}
