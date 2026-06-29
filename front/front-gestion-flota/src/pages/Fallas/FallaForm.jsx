import { useEffect, useState } from "react";
import { listarVehiculos } from "../../services/vehiculoService";
import "../Vehiculos/VehiculoForm.css";

function FallaForm({ onSubmit, falla }) {

    const [vehiculos, setVehiculos] = useState([]);

    const initialForm = {
        vehiculoId: "",
        fecha: "",
        descripcion: "",
        prioridad: "",
        estado: ""
    };

    const [form, setForm] = useState(initialForm);

    useEffect(() => {
        cargarVehiculos();
    }, []);

    useEffect(() => {

        if (falla) {
            setForm({
                vehiculoId: falla.vehiculoId || "",
                fecha: falla.fecha || "",
                descripcion: falla.descripcion || "",
                prioridad: falla.prioridad || "",
                estado: falla.estado || ""
            });
        } else {
            setForm(initialForm);
        }

    }, [falla]);

    const cargarVehiculos = async () => {
        const response = await listarVehiculos();
        setVehiculos(response.data);
    };

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit(form);
    };

    return (

        <form className="vehiculo-form" onSubmit={handleSubmit}>

            <select
                name="vehiculoId"
                value={form.vehiculoId}
                onChange={handleChange}
            >
                <option value="">Seleccione Vehículo</option>

                {vehiculos?.map((v) => (
                    <option key={v.id} value={v.id}>
                        {v.patente} - {v.marca}
                    </option>
                ))}

            </select>

            <input
                type="date"
                name="fecha"
                value={form.fecha}
                onChange={handleChange}
            />

            <textarea
                name="descripcion"
                placeholder="Descripción de la falla"
                value={form.descripcion}
                onChange={handleChange}
            />

            <select
                name="prioridad"
                value={form.prioridad}
                onChange={handleChange}
            >
                <option value="">Seleccione prioridad</option>
                <option value="Baja">Baja</option>
                <option value="Media">Media</option>
                <option value="Alta">Alta</option>
                <option value="Crítica">Crítica</option>
            </select>

            <select
                name="estado"
                value={form.estado}
                onChange={handleChange}
            >
                <option value="">Seleccione estado</option>
                <option value="Pendiente">Pendiente</option>
                <option value="En proceso">En proceso</option>
                <option value="Resuelta">Resuelta</option>
            </select>

            <button type="submit">
                {falla ? "Actualizar" : "Guardar"}
            </button>

        </form>

    );
}

export default FallaForm;