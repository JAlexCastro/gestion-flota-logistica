import api from "./api";

export const listarUsuarios = async () => {

    const response = await api.get("/usuarios/list");

    return response.data;

};

export const crearUsuario = async (data) => {

    const response = await api.post("/usuarios/save", data);

    return response.data;

};

export const actualizarUsuario = async (id, data) => {

    const response = await api.put(`/usuarios/update/${id}`, data);

    return response.data;

};

export const eliminarUsuario = async (id) => {

    const response = await api.delete(`/usuarios/delete/${id}`);

    return response.data;

};