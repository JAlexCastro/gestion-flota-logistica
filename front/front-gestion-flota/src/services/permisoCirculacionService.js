import api from "./api";

export const listarPermisos = async () => {

    const response = await api.get("/permisos-circulacion/list");

    return response.data;

};

export const crearPermiso = async (data) => {

    try {

        const response = await api.post("/permisos-circulacion/save", data);

        return response.data;

    } catch (error) {

        if (error.response) {

            throw new Error(error.response.data.message);

        }

        throw new Error("No fue posible conectar con el servidor.");

    }

};

export const actualizarPermiso = async (id, data) => {

    const response = await api.put(`/permisos-circulacion/update/${id}`, data);

    return response.data;

};

export const eliminarPermiso = async (id) => {

    const response = await api.delete(`/permisos-circulacion/delete/${id}`);

    return response.data;

};