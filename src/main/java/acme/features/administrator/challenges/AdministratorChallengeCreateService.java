
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
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorChallengeCreateService implements AbstractCreateService<Administrator, Challenge> {

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

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

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
	public Challenge instantiate(final Request<Challenge> request) {

		Challenge result;

		result = new Challenge();

		Money moneyB, moneyS, moneyG;
		moneyB = new Money();
		moneyB.setAmount(0.0);
		moneyB.setCurrency("EUR");
		result.setRewardBronze(moneyB);

		moneyS = new Money();
		moneyS.setAmount(0.0);
		moneyS.setCurrency("EUR");
		result.setRewardSilver(moneyS);

		moneyG = new Money();
		moneyG.setAmount(0.0);
		moneyG.setCurrency("EUR");
		result.setRewardGold(moneyG);

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nowplus7 = now.plus(8, ChronoUnit.DAYS);
		Date date = Timestamp.valueOf(nowplus7);
		result.setDeadline(date);

		return result;
	}

	@Override
	public void validate(final Request<Challenge> request, final Challenge entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isEuroZoneB, isEuroZoneS, isEuroZoneG, isRewardBValid, isRewardSValid, isRewardGValid, isOneWeekLater, rbNotEmpty, rsNotEmpty, rgNotEmpty;

		rbNotEmpty = !request.getModel().getAttribute("rewardBronze").toString().isEmpty();
		rsNotEmpty = !request.getModel().getAttribute("rewardSilver").toString().isEmpty();
		rgNotEmpty = !request.getModel().getAttribute("rewardGold").toString().isEmpty();

		if (rbNotEmpty && rsNotEmpty && rgNotEmpty) {
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
	public void create(final Request<Challenge> request, final Challenge entity) {

		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		this.repository.save(entity);

	}
}
