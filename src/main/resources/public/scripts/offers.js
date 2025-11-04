import { postOffer } from './services.js';
import ws from './notificationWebSocket.js';

// const modal = document.querySelector('.modal')
const offerForm = document.querySelector('#offer-form');
const offerList = document.querySelector('#offer-list');
const currentOffer = document.querySelector('#current-offer');

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

const toCurrency = (price) => new Intl.NumberFormat('en-US', {
  style: 'currency',
  currency: 'USD',
}).format(price);

const listElement = ({ name, price }) => {
  const nameSpan = document.createElement('span');
  nameSpan.innerHTML = name;
  const priceSpan = document.createElement('span');
  priceSpan.className = "font-semibold text-[#E26038]";
  priceSpan.innerHTML = `${toCurrency(price)} USD`;
  
  const listItem = document.createElement('div');
  listItem.className = "flex justify-between items-center";
  listItem.appendChild(nameSpan);
  listItem.appendChild(priceSpan);

  return listItem;
}

const addToList = ({ name, price }) => {
  offerList.appendChild(listElement({ name, price }));
  currentOffer.innerHTML = toCurrency(price);
}

offerForm.addEventListener('submit', addOffer);

ws.onmessage = function(event) {
  const rawData = event.data;
  const data = JSON.parse(rawData);
  addToList({ name: data.name, price: data.price })
  console.log("Message received: " + data.message);
};
