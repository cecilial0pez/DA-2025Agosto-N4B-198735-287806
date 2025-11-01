document.addEventListener('DOMContentLoaded', function () {

  function showMsg(container, type, text) {
    if (!container) return;
    container.className = 'msg ' + (type === 'error' ? 'error' : 'success');
    container.textContent = text;
  }

  async function postForm(endpoint, form, msgEl) {
    const fd = new FormData(form);
    try {
      const res = await fetch(endpoint, {
        method: 'POST',
        body: fd,
        credentials: 'same-origin'
      });
      if (!res.ok) {
        showMsg(msgEl, 'error', 'Error de comunicación con el servidor.');
        return;
      }
      const data = await res.json();
      console.log(data);
      if (Array.isArray(data) && data.length > 0) {
        if (data[0].id === 'loginExitoso') {
          showMsg(msgEl, 'success', 'Login correcto. Redirigiendo...');
          const ruta = data[0].parametro || '/';
          setTimeout(() => window.location.href = ruta, 500);
          return;
        } else {
          const texto = data[0].parametro || 'Credenciales incorrectas.';
          showMsg(msgEl, 'error', texto);
          return;
        }
      } else {
        showMsg(msgEl, 'error', 'Respuesta inesperada del servidor.');
      }
    } catch (err) {
      console.error(err);
      showMsg(msgEl, 'error', 'Ocurrió un error inesperado.');
    }
  }

  function wireForm(formId, endpoint) {
    const form = document.getElementById(formId);
    if (!form) return;
    const msg = form.querySelector('#msg');

    form.addEventListener('submit', function (e) {
      e.preventDefault();
      msg.textContent = '';
      const ced = form.querySelector('[name=cedula]');
      const pwd = form.querySelector('[name=password]');
      if (!ced || !pwd) return;
      if (ced.value.trim() === '' || pwd.value.trim() === '') {
        showMsg(msg, 'error', 'Complete todos los campos.');
        return;
      }
      showMsg(msg, 'success', 'Enviando...');
      postForm(endpoint, form, msg);
    });
  }

  wireForm('formProp', '/loginProp');
  wireForm('formAdmin', '/loginAdm');
});