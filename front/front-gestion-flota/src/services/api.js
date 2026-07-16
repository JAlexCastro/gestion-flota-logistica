import axios from "axios";

///const api = axios.create({ baseURL: "http://localhost:8080" });

const api = axios.create({

    baseURL: "https://gestion-flota-logistica.onrender.com"
});

// Agrega el JWT a todas las peticiones
api.interceptors.request.use(

    (config) => {

        const token = localStorage.getItem("token");

        if (token) {

            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Si el token expira, vuelve al login
api.interceptors.response.use(
    (response) => response,
    (error) => {

        if (error.response?.status === 401) {

            localStorage.removeItem("token");
            localStorage.removeItem("usuario");

            window.location.href = "/login";
        }

        return Promise.reject(error);
    }
);

export default api;