
package acme.features.administrator.challenges;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.challenges.Challenge;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorChallengeUpdateService implements AbstractUpdateService<Administrator, Challenge> {

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

		boolean isEuroZoneB, isEuroZoneS, isEuroZoneG, isRewardBValid, isRewardSValid, isRewardGValid, isOneWeekLater;

		boolean rb, rs, rg;
		rb = !request.getModel().getAttribute("rewardBronze").toString().isEmpty();
		rs = !request.getModel().getAttribute("rewardSilver").toString().isEmpty();
		rg = !request.getModel().getAttribute("rewardGold").toString().isEmpty();

		if (rb && rs && rg) {
			Money moneyB, moneyS, moneyG;
			String eur = "EUR";

			moneyB = entity.getRewardBronze();

			moneyS = entity.getRewardSilver();

			moneyG = entity.getRewardGold();

			isEuroZoneB = moneyB.getCurrency().contains(eur);
			errors.state(request, isEuroZoneB, "rewardBronze", "administrator.challenge.error.money-no-euro");

			isEuroZoneS = moneyS.getCurrency().contains(eur);
			errors.state(request, isEuroZoneS, "rewardSilver", "administrator.challenge.error.money-no-euro");

			isEuroZoneG = moneyG.getCurrency().contains(eur);
			errors.state(request, isEuroZoneG, "rewardGold", "administrator.challenge.error.money-no-euro");

			isRewardBValid = moneyB.getAmount() < moneyS.getAmount() && moneyB.getAmount() < moneyG.getAmount();
			errors.state(request, isRewardBValid, "rewardBronze", "administrator.challenge.error.rewardNoValidB");

			isRewardSValid = moneyB.getAmount() < moneyS.getAmount() && moneyS.getAmount() < moneyG.getAmount();
			errors.state(request, isRewardSValid, "rewardSilver", "administrator.challenge.error.rewardNoValidS");

			isRewardGValid = moneyB.getAmount() < moneyG.getAmount() && moneyS.getAmount() < moneyG.getAmount();
			errors.state(request, isRewardGValid, "rewardGold", "administrator.challenge.error.rewardNoValidG");

		}
		if (!request.getModel().getAttribute("deadline").equals("")) {

			LocalDateTime now = LocalDateTime.now();
			LocalDateTime nowplus7 = now.plus(7, ChronoUnit.DAYS);
			Date date = Timestamp.valueOf(nowplus7);
			isOneWeekLater = entity.getDeadline().after(date);
			errors.state(request, isOneWeekLater, "deadline", "administrator.challenge.error.deadline");
		}
	}

	@Override
	public void update(final Request<Challenge> request, final Challenge entity) {

		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		this.repository.save(entity);

	}
}
