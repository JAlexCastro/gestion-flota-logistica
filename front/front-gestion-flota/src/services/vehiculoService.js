import api from "./api";

export const listarVehiculos = async () => {

    const response = await api.get("/vehiculos/list");

    return response.data;

};

export const obtenerVehiculo = async (id) => {

    const response = await api.get(`/vehiculos/${id}`);

    return response.data;

};

export const crearVehiculo = async (vehiculo) => {

    const response = await api.post("/vehiculos/save", vehiculo);

    return response.data;

};

export const actualizarVehiculo = async (id, vehiculo) => {

    const response = await api.put(`/vehiculos/update/${id}`, vehiculo);

    return response.data;

};

export const eliminarVehiculo = async (id) => {

    const response = await api.delete(`/vehiculos/delete/${id}`);

    return response.data;

};