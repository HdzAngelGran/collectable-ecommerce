const BASE_URL = 'http://localhost:8080/api/v1';

export const postOffer = async ({ itemUuid, name, amount }) => {
  const url = `${BASE_URL}/items/${itemUuid}/offers`;
  return await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ name, amount }),
  })
    .then((response) => response.json())
    .then((data) => {
      return data
    })
    .catch((error) => {
      console.error(error)
      return { code: error.code, message: error.message }
    });
}
