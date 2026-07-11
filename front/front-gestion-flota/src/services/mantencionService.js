import api from "./api";

export const listarMantenciones = async () => {
    const response = await api.get("/mantenciones/list");
    return response.data;
};

export const crearMantencion = async (data) => {
    const response = await api.post("/mantenciones/save", data);
    return response.data;
};

export const actualizarMantencion = async (id, data) => {
    const response = await api.put(`/mantenciones/update/${id}`, data);
    return response.data;
};

export const eliminarMantencion = async (id) => {
    const response = await api.delete(`/mantenciones/delete/${id}`);
    return response.data;
};