import { useEffect, useState } from "react";
import { listarVehiculos } from "../../services/vehiculoService";
import "../Vehiculos/VehiculoForm.css";

function RevisionTecnicaForm({ onSubmit, revision }) {

    const [vehiculos, setVehiculos] = useState([]);

    const [form, setForm] = useState({
        vehiculoId: "",
        fechaRevision: "",
        fechaVencimiento: "",
        estado: "",
        observacion: ""
    });

    useEffect(() => {
        cargarVehiculos();
    }, []);

    useEffect(() => {
        if (revision) {
            setForm({
                vehiculoId: revision.vehiculoId || "",
                fechaRevision: revision.fechaRevision || "",
                fechaVencimiento: revision.fechaVencimiento || "",
                estado: revision.estado || "",
                observacion: revision.observacion || ""
            });
        }
    }, [revision]);

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

                {vehiculos?.map(v => (
                    <option key={v.id} value={v.id}>
                        {v.patente} - {v.marca}
                    </option>
                ))}
            </select>

            <input
                type="date"
                name="fechaRevision"
                value={form.fechaRevision}
                onChange={handleChange}
            />

            <input
                type="date"
                name="fechaVencimiento"
                value={form.fechaVencimiento}
                onChange={handleChange}
            />

            <input
                name="estado"
                placeholder="Estado"
                value={form.estado}
                onChange={handleChange}
            />

            <input
                name="observacion"
                placeholder="Observación"
                value={form.observacion}
                onChange={handleChange}
            />

            <button type="submit">
                {revision ? "Actualizar" : "Guardar"}
            </button>

        </form>
    );
}

export default RevisionTecnicaForm;