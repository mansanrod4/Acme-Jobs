
package acme.features.administrator.challenges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.challenges.Challenge;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractDeleteService;

@Service
public class AdministratorChallengeDeleteService implements AbstractDeleteService<Administrator, Challenge> {

	//Internal state

	@Autowired
	AdministratorChallengeRepository repository;


	//AbstractUpdateService<Administrator, Announcement> interface

	@Override
	public boolean authorise(final Request<Challenge> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Challenge> request, final Challenge entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment");

	}

	@Override
	public void unbind(final Request<Challenge> request, final Challenge entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "deadline", "description", "goalBronze", "goalSilver", "goalGold", "rewardBronze", "rewardSilver", "rewardGold");
	}

	@Override
	public Challenge findOne(final Request<Challenge> request) {
		assert request != null;

		Challenge result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);
		return result;
	}

	@Override
	public void validate(final Request<Challenge> request, final Challenge entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isEuroZoneB, isEuroZoneS, isEuroZoneG;
		Money money;
		String eur = "EUR";

		money = entity.getRewardBronze();
		String moneyB = money.toString();
		money = entity.getRewardSilver();
		String moneyS = money.toString();

		money = entity.getRewardGold();
		String moneyG = money.toString();

		isEuroZoneB = moneyB.contains(eur);
		errors.state(request, isEuroZoneB, "rewardBronze", "administrator.challenge.error.money-no-euro");
		isEuroZoneS = moneyS.contains(eur);
		errors.state(request, isEuroZoneS, "rewardSilver", "administrator.challenge.error.money-no-euro");
		isEuroZoneG = moneyG.contains(eur);
		errors.state(request, isEuroZoneG, "rewardGold", "administrator.challenge.error.money-no-euro");

	}

	@Override
	public void delete(final Request<Challenge> request, final Challenge entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
	}
}
