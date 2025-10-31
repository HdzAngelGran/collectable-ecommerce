import { postOffer } from './services.js';

// const modal = document.querySelector('.modal')
const offerForm = document.querySelector('#offer-form');


const errorModal = (error) => {
  console.error(error);
  return;
  modal.children.namedItem('error-code').innerHTML = error.code
  modal.children.namedItem('error-message').innerHTML = error.message
  modal.show()
}

const addOffer = (event) => {
  event.preventDefault();

  const form = event.target;

  console.log(form);
  const itemUuid = form['item-uuid'].value;
  const name = form['item-name'].value;
  const amount = form['item-amount'].value;

  if (itemUuid == null || name == null || amount == null)
    errorModal({ code: error.code, message: error.message });

  postOffer({ itemUuid, name, amount });
}

offerForm.addEventListener('submit', addOffer);
