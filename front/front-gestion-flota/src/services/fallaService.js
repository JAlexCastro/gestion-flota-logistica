import api from "./api";

export const listarFallas = async () => {
    const response = await api.get("/fallas/list");
    return response.data;
};

export const crearFalla = async (data) => {
    const response = await api.post("/fallas/save", data);
    return response.data;
};

export const actualizarFalla = async (id, data) => {
    const response = await api.put(`/fallas/update/${id}`, data);
    return response.data;
};

export const eliminarFalla = async (id) => {
    const response = await api.delete(`/fallas/delete/${id}`);
    return response.data;
};